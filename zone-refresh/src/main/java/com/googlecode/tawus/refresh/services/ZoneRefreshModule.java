package com.googlecode.tawus.refresh.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.LibraryMapping;

public class ZoneRefreshModule
{
   
   @Contribute(ComponentClassResolver.class)
   public void provideComponentClassResolver(Configuration<LibraryMapping> configuration)
   {
      configuration.add(new LibraryMapping("tawus", "com.googlecode.tawus.refresh"));
   }
   
}
