package com.googlecode.tawus.refresh.mixins;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.test.TapestryTestCase;
import org.testng.annotations.Test;

public class ZoneRefreshTest extends TapestryTestCase
{
   
   @Test
   public void check_add_javascript_calls_component_resources_to_create_url()
   {
      Object [] context = new Object[]{ "something", "somewhere" };
      ComponentResources resources = mockComponentResources();
      JavaScriptSupport javaScriptSupport = mockJavaScriptSupport();
      Link link = mockLink();
      
      Zone zone = mockZone();
      
      ZoneRefresh zoneRefresh = new ZoneRefresh(context, resources, javaScriptSupport, zone, "refresh");
      
      expect(resources.createEventLink("refresh", context)).andReturn(link);
      expect(link.toAbsoluteURI()).andReturn("mylink");
      
      JSONObject params = new JSONObject();
      params.put("period", 0);
      params.put("id", zone.getClientId());
      params.put("URL", "mylink");
      javaScriptSupport.addInitializerCall(InitializationPriority.LATE, "zoneRefresh", params);
      
      replay();
      
      zoneRefresh.addJavaScript();
      
      verify();
   }

   private Zone mockZone()
   {
      return new Zone()
      {
         @Override
         public String getClientId()
         {
            return "zoneId";
         }
         
      };
   }

}
