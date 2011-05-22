package plasticdemo.transforms

import org.apache.tapestry5.internal.plastic.StandardDelegate
import org.apache.tapestry5.plastic.PlasticManager

import spock.lang.Specification


/**
 * A simple test for {@link plasticdemo.transforms.ToStringTransformer}
 */
class ToStringTest extends Specification {
   def pm
   
   def setup(){
      //Create a plastic manager and pass on the controlled package and the transformer
      pm = PlasticManager.withContextClassLoader().packages(["plasticdemo.controlled"]).
         delegate(new StandardDelegate(new ToStringTransformer())).create()
   }
   
   def "test if a class in controlled package has our toString method"(){
      def foo = pm.getClassInstantiator("plasticdemo.controlled.Foo").newInstance()
      expect: foo.toString().equals("Modified by <ToStringTransformer>")
   }
}
