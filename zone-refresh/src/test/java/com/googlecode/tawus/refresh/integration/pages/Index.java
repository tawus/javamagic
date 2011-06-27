package com.googlecode.tawus.refresh.integration.pages;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;

public class Index
{
   @Persist
   @Property
   private int counter;
   
   @Persist
   @Property
   private int slowCounter;
   
   @InjectComponent
   private Zone zone;
   
   @InjectComponent
   private Zone slowZone;
   
   void setupRender()
   {
      slowCounter = 0;
      counter = 0;
   }
   
   Zone onRefreshFromZone()
   {
      counter++;
      return zone;   
   }
   
   Zone onRefreshFromSlowZone()
   {
      slowCounter++;
      return slowZone;
   }

}
