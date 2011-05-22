package plasticdemo.services;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.tapestry5.plastic.InstructionBuilder;
import org.apache.tapestry5.plastic.InstructionBuilderCallback;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticClassTransformer;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.plastic.PlasticManager;

public class ChainBuilderImpl implements ChainBuilder {
   private PlasticManager pm;

   public ChainBuilderImpl(PlasticManager pm) {
      this.pm = pm;
   }

   @SuppressWarnings("unchecked")
   public <T> T build(Class<T> commandInterface, List<T> commands) {
      // Create a new class implementing this interface
      return (T) pm.createClass(Object.class,
               new ChainBuilderTransformer<T>(commandInterface, commands)).newInstance();
   }

   static public class ChainBuilderTransformer<T> implements PlasticClassTransformer {
      private final Class<T> commandInterface;
      private final List<T> commands;

      public ChainBuilderTransformer(Class<T> commandInterface, List<T> commands) {
         this.commandInterface = commandInterface;
         this.commands = commands;
      }

      public void transform(PlasticClass pc) {
         // Implement the interface
         pc.introduceInterface(commandInterface);

         // Add a field which will be an array containing the commands
         final Object[] arrayOfCommands = commands.toArray();
         final PlasticField arrayOfCommandsField = pc.introduceField(Object[].class, "_commands$"
                  + commandInterface.getSimpleName());
         arrayOfCommandsField.inject(arrayOfCommands);

         // For each method create chain
         for (Method method : commandInterface.getMethods()) {
            createChain(pc, arrayOfCommandsField, method);
         }

      }

      private void createChain(final PlasticClass pc, final PlasticField arrayOfCommandsField,
               final Method method) {
         pc.introduceMethod(method).changeImplementation(new InstructionBuilderCallback() {
            public void doBuild(InstructionBuilder builder) {
               builder.loadThis().getField(arrayOfCommandsField)
                        .iterateArray(new InstructionBuilderCallback() {
                           public void doBuild(InstructionBuilder builder) {
                              builder.loadArguments().invoke(method);
                              return;
                           }
                        });
               builder.returnDefaultValue();
            }
         });
      }
   }
}
