package plasticdemo.transforms

import org.apache.tapestry5.internal.plastic.StandardDelegate
import org.apache.tapestry5.plastic.PlasticManager

import plasticdemo.services.ChainBuilderImpl
import plasticdemo.services.MyService;
import spock.lang.Specification

class ChainBuilderTest extends Specification {
   def pm

   def setup(){
      pm = PlasticManager.withContextClassLoader().delegate(new StandardDelegate(new RunTransformer())).packages(["plasticdemo.controlled"]).create();
   }

   def "test if foo is runnable"(){
      setup:
      def chainBuilder = new ChainBuilderImpl(pm)
      MyService service1 = Mock(MyService)
      MyService service2 = Mock(MyService)
      def chain = chainBuilder.build(MyService, [service1, service2])
      when:
      chain.process()
      then:
      1 * service1.process()
      1 * service2.process()
   }
}
