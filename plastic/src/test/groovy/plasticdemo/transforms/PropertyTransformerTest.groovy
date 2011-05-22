package plasticdemo.transforms

import org.apache.tapestry5.internal.plastic.StandardDelegate
import org.apache.tapestry5.plastic.PlasticManager

import spock.lang.Specification


/**
 * Tests {@link plasticdemo.transforms.PropertyTransformer}
 */
class PropertyTransformerTest extends Specification {
   def pm
   
   def setup(){
      pm = PlasticManager.withContextClassLoader().delegate(
         new StandardDelegate(new PropertyTransformer())).
         packages(["plasticdemo.controlled"]).create();
   }
   
   def "test if setter/getters have been introduced into foo"(){
      when:
      def foo = pm.getClassInstantiator("plasticdemo.controlled.Foo").newInstance()
      
      def setName = foo.class.getDeclaredMethod("setName", String.class)
      def setNum = foo.class.getDeclaredMethod("setNum", int.class)
      then:
      setName != null
      when:
      def getName = foo.class.getDeclaredMethod("getName")
      def getNum = foo.class.getDeclaredMethod("getNum")
      then:
      getName != null
      getNum != null
      when:
      setName.invoke(foo, "bar")
      setNum.invoke(foo, 55)
      then:
      getName.invoke(foo).equals("bar")
      getNum.invoke(foo).equals(55)
   }
}
