package plasticdemo.services;

import java.lang.reflect.Method;

import org.apache.tapestry5.plastic.ClassInstantiator;
import org.apache.tapestry5.plastic.InstructionBuilder;
import org.apache.tapestry5.plastic.InstructionBuilderCallback;
import org.apache.tapestry5.plastic.MethodDescription;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticClassTransformer;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.plastic.PlasticManager;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.plastic.PlasticUtils;

public class MethodShadowBuilderImpl implements MethodShadowBuilder {
   private PlasticManager pm;

   public MethodShadowBuilderImpl(PlasticManager pm) {
      this.pm = pm;
   }

   public <T> T build(final Object source, final String methodName, final Class<T> interfaceType,
            final Object... args) {
      final String sourceClass = PlasticUtils.toTypeName(source.getClass());
      final String interfaceClass = PlasticUtils.toTypeName(interfaceType);

      // Ensure it is an interface
      if (!interfaceType.isInterface()) {
         throw new RuntimeException(interfaceType.getName() + " is not an interface");
      }

      // Get argument types
      final Class<?>[] parameterTypes = new Class<?>[args.length];
      for (int i = 0; i < parameterTypes.length; ++i) {
         parameterTypes[i] = args[i].getClass();
      }

      final Method matchingMethod = getCompatibleMethod(source.getClass(), methodName,
               parameterTypes);
      if (matchingMethod == null) {
         throw new RuntimeException("Could not find method " + methodName + " with arguments "
                  + parameterTypes);
      }

      // Create a new class which implements the interface
      final ClassInstantiator<T> instantiator = pm.createProxy(interfaceType,
               new PlasticClassTransformer() {

                  public void transform(PlasticClass plasticClass) {
                     final PlasticField field = plasticClass
                              .introduceField(sourceClass, "_$source");
                     field.inject(source);

                     final PlasticField[] argumentFields = new PlasticField[parameterTypes.length];
                     for (int i = 0; i < argumentFields.length; ++i) {
                        argumentFields[i] = plasticClass.introduceField(args[i].getClass(),
                                 "_$field" + i);
                        argumentFields[i].inject(args[i]);
                     }

                     PlasticMethod delegateMethod = plasticClass
                              .introduceMethod(new MethodDescription(interfaceClass, "_delegate"));
                     delegateMethod.changeImplementation(new InstructionBuilderCallback() {

                        public void doBuild(InstructionBuilder builder) {
                           builder.loadThis().getField(field);

                           for (int i = 0; i < argumentFields.length; ++i) {
                              builder.loadThis().getField(argumentFields[i]).castOrUnbox(
                                       PlasticUtils.toTypeName(matchingMethod.getParameterTypes()[i]));
                           }

                           builder.invokeVirtual(sourceClass, interfaceClass, methodName,
                                    PlasticUtils.toTypeNames(matchingMethod.getParameterTypes()));
                           builder.returnResult();
                        }

                     });

                     for (Method method : interfaceType.getMethods()) {
                        plasticClass.introduceMethod(method).delegateTo(delegateMethod);
                     }

                  }

               });

      return instantiator.newInstance();
   }

   /**
    * Get a compatible constructor to the supplied parameter types.
    * 
    * @param clazz
    *           the class which we want to construct
    * @param parameterTypes
    *           the types required of the constructor
    * 
    * @return a compatible constructor or null if none exists
    */
   public static Method getCompatibleMethod(Class<?> clazz, String methodName,
            Class<?>[] parameterTypes) {
      Method[] methods = clazz.getDeclaredMethods();
      for (int i = 0; i < methods.length; i++) {
         if (!methods[i].getName().equals(methodName)) {
            continue;
         }

         if (methods[i].getParameterTypes().length == (parameterTypes != null ? parameterTypes.length
                  : 0)) {
            Class<?>[] methodTypes = methods[i].getParameterTypes();
            boolean isCompatible = true;
            for (int j = 0; j < (parameterTypes != null ? parameterTypes.length : 0); j++) {
               if (!methodTypes[j].isAssignableFrom(parameterTypes[j])) {
                  if(parameterTypes[j].isPrimitive()){
                     Class<?> wrapperType = PlasticUtils.toWrapperType(parameterTypes[j]);
                     if(methodTypes[j].isAssignableFrom(wrapperType)){
                        isCompatible = false;
                     }
                  }
                  
                  if (!isCompatible && methodTypes[j].isPrimitive()) {
                     Class<?> wrapperType = PlasticUtils.toWrapperType(methodTypes[j]);
                     if(!wrapperType.isAssignableFrom(parameterTypes[j])){
                        isCompatible = false;
                     }
                  }
               }
            }
            if (isCompatible) {
               return methods[i];
            }
         }
      }
      return null;
   }
}
