package com.googlecode.tawus.ajaxdemo.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import com.googlecode.tawus.ajaxdemo.Foo;

/**
 * A simple AjaxFormLoop example which shows how to add objects to a list using
 * AjaxFormLoop
 */
public class SimpleLoop
{
   @Property
   @Persist
   private List<Foo> foos;

   @SuppressWarnings("unused")
   @Property
   private Foo foo;

   void onActivate()
   {
      if(foos == null)
      {
         foos = new ArrayList<Foo>();   
      }
   }
   
   void setupRender()
   {
      foos.removeAll(Collections.singleton(null));
   }
   
   public ValueEncoder<Foo> getEncoder(){
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

}
