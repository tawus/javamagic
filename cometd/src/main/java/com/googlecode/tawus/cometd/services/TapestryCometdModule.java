package com.googlecode.tawus.cometd.services;

import java.util.List;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.services.PropertyShadowBuilder;
import org.apache.tapestry5.ioc.services.RegistryShutdownHub;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.cometd.bayeux.server.BayeuxServer;
import org.slf4j.Logger;

import com.googlecode.tawus.cometd.CometdConstants;
import com.googlecode.tawus.cometd.internal.BayeuxServerSourceImpl;
import com.googlecode.tawus.cometd.internal.CometdRequestFilter;

public class TapestryCometdModule {

   public static void bind(ServiceBinder binder) {;
   }

   public BayeuxServerSource buildBayeuxServerSource(
         List<BayeuxServerConfigurer> configurers, ObjectLocator locator,
         Logger logger,
         RegistryShutdownHub registryShutdownHub) {
      BayeuxServerSourceImpl impl = new BayeuxServerSourceImpl(configurers, locator, logger);
      registryShutdownHub.addRegistryShutdownListener(impl);
      return impl;
   }

   public static void contributeHttpServletRequestHandler(
         OrderedConfiguration<HttpServletRequestFilter> filters) {
      filters.addInstance("Cometd", CometdRequestFilter.class);
   }

   public BayeuxServer buildBayeuxServer(BayeuxServerSource bayeuxServerSource,
         PropertyShadowBuilder shadowBuilder) {
      return shadowBuilder.build(bayeuxServerSource, "bayeux",
            BayeuxServer.class);
   }

   public static void contributeFactoryDefaults(
         MappedConfiguration<String, String> configuration) {
      configuration.add(CometdConstants.CONTEXT_PATH, "/cometd/");
   }

}
