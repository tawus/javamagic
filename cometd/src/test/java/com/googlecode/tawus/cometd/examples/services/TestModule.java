package com.googlecode.tawus.cometd.examples.services;

import java.util.List;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.cometd.server.BayeuxServerImpl;

import com.googlecode.tawus.cometd.services.BayeuxServerConfigurer;
import com.googlecode.tawus.cometd.services.TapestryCometdModule;

@SubModule(TapestryCometdModule.class)
public class TestModule {
   
   public void contributeBayeuxServerSource(
         OrderedConfiguration<BayeuxServerConfigurer> configurers){
      configurers.add("bayeuxServices", new BayeuxServerConfigurer(){

         public void configure(BayeuxServerImpl bayeuxServerImpl,
               List<Class<?>> services) {
            services.add(HelloService.class);
         }
         
      });
   }
   
   public static void contributeClasspathAssetAliasManager(
         MappedConfiguration<String, String> configuration) {
      configuration.add("dojo", "dojo");
      configuration.add("dojox", "dojox");
      configuration.add("dijit", "dijit");
      configuration.add("org", "org");
   }
   
}
