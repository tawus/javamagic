package com.googlecode.tawus.gravatar.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.LibraryMapping;

import com.googlecode.tawus.gravatar.internal.GravatarURLCreatorImpl;

public class GravatarModule {

   public static void bind(ServiceBinder binder){
      binder.bind(GravatarURLCreator.class, GravatarURLCreatorImpl.class);
   }
   
   public static void contributeFactoryDefaults(MappedConfiguration<String, String> defaults){
      defaults.add("gravatar-base-url", "http://gravatar.com/");//Remember the trailing slash
   }
   
   @Contribute(ComponentClassResolver.class)
   public void provideComponentClassResolver(Configuration<LibraryMapping> configuration) {
      configuration.add(new LibraryMapping("tawus", "com.googlecode.tawus.gravatar"));
   }

}
