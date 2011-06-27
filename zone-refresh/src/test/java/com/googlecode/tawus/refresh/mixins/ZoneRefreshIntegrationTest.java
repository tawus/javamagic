package com.googlecode.tawus.refresh.mixins;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

public class ZoneRefreshIntegrationTest extends SeleniumTestCase
{
   @Test
   public void test_if_two_zones_get_updated() throws Exception
   {
      openBaseURL();
      checkZoneValues(3);
   }

   private void checkZoneValues(int times) throws Exception
   {
      Thread.sleep(1500);
      for(int i = 0; i < times; ++i)
      {
         assertText("zone", String.valueOf(i));
         Thread.sleep(5000);
      }
   }

}
