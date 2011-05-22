package plasticdemo.transforms;

import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticClassTransformer;

/**
 * A simple class transformer which adds a toString method to the
 * classes to be transformed
 */
public class ToStringTransformer implements PlasticClassTransformer {

   /**
    * Adds a toString() method to the class
    */
   public void transform(PlasticClass plasticClass) {
      plasticClass.addToString("Modified by <ToStringTransformer>");
   }

}
