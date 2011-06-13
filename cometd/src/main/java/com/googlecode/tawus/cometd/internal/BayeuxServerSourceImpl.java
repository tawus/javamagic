package com.googlecode.tawus.cometd.internal;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.services.RegistryShutdownListener;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.bayeux.server.ServerTransport;
import org.cometd.java.annotation.ServerAnnotationProcessor;
import org.cometd.server.BayeuxServerImpl;
import org.cometd.server.ServerSessionImpl;
import org.cometd.server.transport.HttpTransport;
import org.slf4j.Logger;

import com.googlecode.tawus.cometd.services.BayeuxServerConfigurer;
import com.googlecode.tawus.cometd.services.BayeuxServerSource;

public class BayeuxServerSourceImpl implements BayeuxServerSource,
      RegistryShutdownListener {

   private BayeuxServerImpl bayeux;

   private List<HttpTransport> allowedTransports;

   private List<Object> annotationServices;

   private ServerAnnotationProcessor annotationProcessor;

   private ObjectLocator locator;

   private List<BayeuxServerConfigurer> configurers;

   private Logger logger;

   private boolean initialized;

   public BayeuxServerSourceImpl(List<BayeuxServerConfigurer> configurers,
         ObjectLocator locator, Logger logger) {
      bayeux = newBayeuxServer();
      this.locator = locator;
      this.configurers = configurers;
      this.logger = logger;
      annotationServices = new ArrayList<Object>();
      allowedTransports = new ArrayList<HttpTransport>();
      initialized = false;
   }

   public synchronized void start(){
      if(initialized){
         throw new RuntimeException("The Bayeux Server is already started");
      }
      
      
      List<Class<?>> annotationServiceClasses = new ArrayList<Class<?>>();
      logger.debug("Configuring Bayeux Server using configurers");
      for(final BayeuxServerConfigurer configurer: configurers){
         configurer.configure(bayeux, annotationServiceClasses);
      }
      
      try {
         logger.debug("Starting server");
         bayeux.start();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
      
      addAllowedTransports();
      
      setupAnnotatedServices(annotationServiceClasses);
      
      initialized = true;
   }

   private void addAllowedTransports() {
      logger.debug("Adding allowed transports");
      for (String transportName : bayeux.getAllowedTransports()) {
         ServerTransport transport = bayeux.getTransport(transportName);
         if (transport instanceof HttpTransport) {
            logger.debug("Adding transport " + transportName);
            allowedTransports.add((HttpTransport) transport);
         }
      }
   }

   private void setupAnnotatedServices(List<Class<?>> annotationServiceClasses) {
      logger.debug("Setting annotation services processor");
      annotationProcessor = new ServerAnnotationProcessor(getBayeux());

      for (Class<?> service : annotationServiceClasses) {
         Object object = locator.autobuild(service);
         logger.info("Building service for interface " + service);
         annotationProcessor.process(object);
         annotationServices.add(object);
      }
   }

   protected BayeuxServerImpl newBayeuxServer() {
      return new BayeuxServerImpl();
   }

   public BayeuxServer getBayeux() {
      return bayeux;
   }

   public BayeuxServerImpl getBayeuxImpl() {
      return bayeux;
   }

   public List<HttpTransport> getAllowedTransports() {
      return allowedTransports;
   }

   public void registryDidShutdown() {
      if (!initialized) {
         return;
      }
      cleanupAnnotatedServices();
      cleanupSessions();

      try {
         bayeux.stop();
      } catch (Exception e) {
         throw new RuntimeException(e);
      } finally {
         bayeux = null;
      }

      allowedTransports.clear();
   }

   private void cleanupAnnotatedServices() {
      if (annotationProcessor != null) {
         for (Object service : annotationServices) {
            logger.debug("Deprocessing " + service);
            annotationProcessor.deprocess(service);
         }
      }
   }

   private void cleanupSessions() {
      for (ServerSession session : bayeux.getSessions()) {
         logger.debug("Cleaning up session : " + session);
         ((ServerSessionImpl) session).cancelSchedule();
      }

   }
}
