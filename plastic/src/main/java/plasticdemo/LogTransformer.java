package plasticdemo;

import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticClassTransformer;

public class LogTransformer implements PlasticClassTransformer {

	public void transform(PlasticClass plasticClass) {
	   @SuppressWarnings("unused")
      MethodAdvice advice = new MethodAdvice(){

		public void advise(MethodInvocation invocation) {
			
		}
		   
	   };
	}
}

