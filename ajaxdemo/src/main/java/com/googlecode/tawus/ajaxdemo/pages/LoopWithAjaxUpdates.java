package com.googlecode.tawus.ajaxdemo.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;

import com.googlecode.tawus.ajaxdemo.Foo;

/**
 * AjaxFormLoop with ajax updates.
 */
public class LoopWithAjaxUpdates
{
   @Persist
   @Property
   private List<Foo> foos;

   @Property
   private Foo foo;

   @InjectComponent
   private Zone zone;
   
   void onActivate()
   {
      if(foos == null)
      {
         foos = new ArrayList<Foo>();
      }

   }
   
   public String getUniqueZoneId()
   {
      return "zone_" + foos.indexOf(foo);
   }
   
   public int getId()
   {
      return foos.indexOf(foo);
   }

   public ValueEncoder<Foo> getEncoder()
   {
      return new ValueEncoder<Foo>()
      {

         public String toClient(Foo foo)
         {
            return String.valueOf(foos.indexOf(foo));
         }

         public Foo toValue(String clientValue)
         {
            return foos.get(Integer.parseInt(clientValue));
         }

      };
   }

   Object onAddRow()
   {
      Foo newFoo = new Foo();
      foos.add(newFoo);
      return newFoo;
   }

   void onRemoveRow(Foo newFoo)
   {
      foos.set(foos.indexOf(newFoo), null);
   }

   void onSuccess()
   {
      foos.removeAll(Collections.singleton(null));
   }

   Object onZoneUpdate(int index)
   {
      foo = foos.get(index);
      return zone.getBody();
   }
}
