package plasticdemo.transforms

import org.apache.tapestry5.internal.plastic.StandardDelegate
import org.apache.tapestry5.plastic.PlasticManager

import spock.lang.Specification


class RunTransformerTest extends Specification {
   def pm
   
   def setup(){
      pm = PlasticManager.withContextClassLoader().delegate(new StandardDelegate(new RunTransformer())).packages(["plasticdemo.controlled"]).create();
   }
   
   def "test if foo is runnable"(){
      setup:
      def foo = pm.getClassInstantiator("plasticdemo.controlled.Foo").newInstance();
      def thread = new Thread((Runnable)foo)
      expect: foo.didRun == false
      when:
      thread.run()
      then:
      foo.didRun == true
   }
}
