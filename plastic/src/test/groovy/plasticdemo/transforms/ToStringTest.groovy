package plasticdemo.transforms

import org.apache.tapestry5.internal.plastic.StandardDelegate
import org.apache.tapestry5.plastic.PlasticManager

import spock.lang.Specification

class ToStringTest extends Specification {
   def pm
   
   def setup(){
      pm = PlasticManager.withContextClassLoader().packages(["plasticdemo.controlled"]).
         delegate(new StandardDelegate(new ToStringTransformer())).create()
   }
   
   def "test a class in controlled package has transformed toString method"(){
      def foo = pm.getClassInstantiator("plasticdemo.controlled.Foo").newInstance()
      expect: foo.toString().equals("Modified by <ToStringTransformer>")
   }
}
