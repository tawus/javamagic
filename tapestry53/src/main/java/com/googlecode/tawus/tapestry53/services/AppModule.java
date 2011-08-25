package com.googlecode.tawus.tapestry53.services;

import org.apache.tapestry5.ioc.ServiceBinder;

public class AppModule
{

   public static void bind(ServiceBinder binder)
   {
      binder.bind(CounterService.class);
   }
   
}
