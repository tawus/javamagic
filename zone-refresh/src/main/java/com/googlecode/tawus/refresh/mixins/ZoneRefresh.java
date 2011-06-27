package com.googlecode.tawus.refresh.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * A mixin to periodically update a zone. 
 * TAP5-746
 * 
 */
@Import(library = "zone-refresh.js")
public class ZoneRefresh
{
   /**
    *  Period is seconds 
    */
   @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
   private int period;
   
   /**
    * Context passed to the event
    */
   @Parameter
   private Object[] context;
   
   /**
    * Event that will be fired periodically
    */
   @Parameter(value = "refresh", defaultPrefix = BindingConstants.LITERAL)
   private String event;
   
   @InjectContainer
   private Zone zone;
   
   @Inject
   private JavaScriptSupport javaScriptSupport;
   
   @Inject
   private ComponentResources resources;
   
   public ZoneRefresh()
   {
   }
   
   //For testing purpose
   ZoneRefresh(Object [] context, ComponentResources resources, JavaScriptSupport javaScriptSupport, Zone zone, String event)
   {
      this.context = context;
      this.resources = resources;
      this.javaScriptSupport = javaScriptSupport;
      this.zone = zone;
      this.event = event;
   }

   @AfterRender
   void addJavaScript()
   {
      JSONObject params = new JSONObject();
      
      params.put("period", period);
      params.put("id", zone.getClientId());
      params.put("URL", createEventLink());
      
      javaScriptSupport.addInitializerCall(InitializationPriority.LATE, "zoneRefresh", params);
   }

   private Object createEventLink()
   {
      Link link = resources.createEventLink(event, context);
      return link.toAbsoluteURI();
   }

}
