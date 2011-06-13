package com.googlecode.tawus.cometd.internal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.apache.tapestry5.services.RequestGlobals;
import org.cometd.server.BayeuxServerImpl;
import org.cometd.server.transport.HttpTransport;
import org.slf4j.Logger;

import com.googlecode.tawus.cometd.CometdConstants;
import com.googlecode.tawus.cometd.services.BayeuxServerSource;

/**
 * This is a replacement for AnnotationCometdServlet. It is responsible for
 * managing the server services
 * 
 */
public class CometdRequestFilter implements HttpServletRequestFilter {

   private BayeuxServerSource bayeuxServerSource;
   private String path;
   private Logger logger;

   public CometdRequestFilter(BayeuxServerSource bayeuxServerSource,
         RequestGlobals requestGlobals, Logger logger,
         @Symbol(CometdConstants.CONTEXT_PATH) @Inject String path) {
      this.bayeuxServerSource = bayeuxServerSource;
      this.path = path.toLowerCase();
      this.logger = logger;

      try {
         bayeuxServerSource.start();
         logger.debug("Allowed protocols are : " + bayeuxServerSource.getAllowedTransports());
      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("Could not start Bayeux Server: "
               + e.getMessage(), e);
      }
   }

   private void setServiceOptions(HttpServletRequest request,
         HttpServletResponse response) {

   }

   public boolean service(HttpServletRequest request,
         HttpServletResponse response, HttpServletRequestHandler handler)
         throws IOException {
      // Not my path, so don't handle it
      if (!request.getRequestURI().startsWith(request.getContextPath() + path)) {
         logger.debug("Skipping " + request.getRequestURI() + " not matching "
               + path);
         return handler.service(request, response);
      }

      logger.debug("Processing request : " + request.getRequestURI());
      if ("OPTIONS".equals(request.getMethod())) {
         setServiceOptions(request, response);
         return true;
      }

      HttpTransport transport = null;
      
      for (HttpTransport allowedTransport : bayeuxServerSource.getAllowedTransports()) {
         if (allowedTransport != null && allowedTransport.accept(request)) {
            transport = allowedTransport;
            break;
         }
      }

      if (transport == null) {
         logger.error("Request " + request.getRequestURI()
               + " Unknown Bayeux Transport");
         response.sendError(HttpServletResponse.SC_BAD_REQUEST,
               "Unknown Bayeux Transport");
      } else {
         BayeuxServerImpl bayeux = bayeuxServerSource.getBayeuxImpl();
         try {
            bayeux.setCurrentTransport(transport);
            transport.setCurrentRequest(request);
            transport.handle(request, response);
         } catch (ServletException e) {
            throw new IOException(e);
         } finally {
            transport.setCurrentRequest(null);
            if (bayeux != null) {
               bayeux.setCurrentTransport(null);
            }
         }
      }

      return true;
   }

}
