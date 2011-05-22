package plasticdemo.transforms;

import org.apache.tapestry5.plastic.FieldHandle;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodDescription;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticClassTransformer;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.plastic.PlasticMethod;

import plasticdemo.annotations.Property;

public class PropertyTransformer implements PlasticClassTransformer {
   public void transform(PlasticClass plasticClass) {
      for (PlasticField field : plasticClass.getFieldsWithAnnotation(Property.class)) {
         introduceGetter(plasticClass, field);

         Property annotation = field.getAnnotation(Property.class);
         if (annotation.write()) {
            introduceSetter(plasticClass, field);
         }
      }
   }

   private void introduceGetter(PlasticClass plasticClass, final PlasticField field) {
      String methodName = toMethodName("get", field.getName());
      MethodDescription getterDescription = new MethodDescription(field.getTypeName(), methodName);
      PlasticMethod method = plasticClass.introduceMethod(getterDescription);
      final FieldHandle fieldHandle = field.getHandle();
      method.addAdvice(new MethodAdvice() {

         public void advise(MethodInvocation invocation) {
            invocation.setReturnValue(fieldHandle.get(invocation.getInstance()));
         }

      });

   }

   private void introduceSetter(PlasticClass plasticClass, final PlasticField field) {
      String methodName = toMethodName("set", field.getName());
      MethodDescription setterDescription = new MethodDescription("void", methodName,
               field.getTypeName());
      PlasticMethod method = plasticClass.introduceMethod(setterDescription);
      final FieldHandle fieldHandle = field.getHandle();
      method.addAdvice(new MethodAdvice(){

         public void advise(MethodInvocation invocation) {
            fieldHandle.set(invocation.getInstance(), invocation.getParameter(0));            
         }
         
      });
   }

   private String toMethodName(String prefix, String name) {
      String methodName = name.replaceAll("^_*", "");
      return prefix + Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);
   }

}
