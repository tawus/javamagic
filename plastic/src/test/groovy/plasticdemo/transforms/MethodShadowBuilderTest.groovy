package plasticdemo.transforms

import org.apache.tapestry5.plastic.PlasticManager

import plasticdemo.services.MethodShadowBuilder
import plasticdemo.services.MethodShadowBuilderImpl
import plasticdemo.services.Service1
import spock.lang.Specification

class MethodShadowBuilderTest extends Specification {

   public static class Service2 {
      public Service1 createService1(Object ... values){
         return new Service1(){
            public String process(){
               return values.toString();
            }
         }
      }
   }
   
   public static class Service3 {
      public Service1 createService1(){
         return new Service1(){
            public String process(){
               return "none";
            }
         }
      }
   }

   public static class Service4 {
      public Service1 createService1(ArrayList<String> values){
         return new Service1(){
            public String process(){
               return values.toString();
            }
         }
      }
   }

   public static class Service5 {
      public Service1 createService1(long value){
         return new Service1(){
            public String process(){
               return String.valueOf(value);
            }
         }
      }
   }
   
   public static class Service6 {
      public Service1 createService1(Long value){
         return new Service1(){
            public String process(){
               return String.valueOf(value);
            }
         }
      }
   }

   public static class Service7 {
      public Service1 createService1(Object value){
         return new Service1(){
            public String process(){
               return String.valueOf(value);
            }
         }
      }
   }
   def pm
   
   def setup(){
      pm = PlasticManager.withContextClassLoader().create();
   }
   
   def "test service creation"(){
      setup:
      MethodShadowBuilder builder = new MethodShadowBuilderImpl(pm);
      Service2 service2 = new Service2();
      when:
      Service1 proxyService = builder.build(service2,  "createService1", Service1, [[] as Object[]] as Object[]);
      then:
      proxyService.process().equals("[]")
      
      when:  
      proxyService = builder.build(service2,  "createService1", Service1, [["foo"] as Object[]] as Object[]);
      then:
      proxyService.process().equals("[foo]")
      
      when:
      proxyService = builder.build(service2,  "createService1", Service1, [["foo", "bar"] as Object[]] as Object[]);
      then:
      proxyService.process().equals("[foo, bar]")
      
      when:
      Service3 service3 = new Service3()
      proxyService = builder.build(service3,  "createService1", Service1);
      then:
      proxyService.process().equals("none")
      
      when:
      Service4 service4 = new Service4()
      proxyService = builder.build(service4,  "createService1", Service1, ["foo", "bar"]);
      then:
      proxyService.process().equals("[foo, bar]")
      
      when:
      Service5 service5 = new Service5()
      proxyService = builder.build(service5,  "createService1", Service1, 20L);
      then:
      proxyService.process().equals("20")

      when:
      Service6 service6 = new Service6()
      proxyService = builder.build(service6,  "createService1", Service1, 20L);
      then:
      proxyService.process().equals("20")
      
      when:
      Service7 service7 = new Service7()
      proxyService = builder.build(service7,  "createService1", Service1, 20L);
      then:
      proxyService.process().equals("20")
   }
   
}
