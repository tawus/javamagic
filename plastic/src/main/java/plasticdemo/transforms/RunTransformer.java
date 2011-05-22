package plasticdemo.transforms;

import java.util.List;

import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodDescription;
import org.apache.tapestry5.plastic.MethodHandle;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticClassTransformer;
import org.apache.tapestry5.plastic.PlasticMethod;

import plasticdemo.annotations.Run;

public class RunTransformer implements PlasticClassTransformer {
   private static final MethodDescription RUN_METHOD = new MethodDescription("void", "run");

   public void transform(PlasticClass plasticClass) {
      final List<PlasticMethod> methods = plasticClass.getMethodsWithAnnotation(Run.class);

      if (methods.size() == 0) {
         return;
      }

      if (methods.size() > 1) {
         throw new RuntimeException("@Run can be only placed on a single method in a class");
      }

      final MethodHandle methodHandle = methods.get(0).getHandle();
      plasticClass.introduceInterface(Runnable.class);
      PlasticMethod runMethod = plasticClass.introduceMethod(RUN_METHOD);
      runMethod.addAdvice(new MethodAdvice(){

         public void advise(MethodInvocation invocation) {
            methodHandle.invoke(invocation.getInstance());            
         }
         
      });
   }

}
