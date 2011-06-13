package com.googlecode.tawus.cometd.examples.pages;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

@Import(library = {"classpath:dojo/dojo.js.uncompressed.js", "application.js"})
public class TestPage {

   @Inject
   private Request request;
   
   public String getContextPath(){
      return request.getContextPath();
   }
}
