package com.googlecode.tawus.gravatar.components;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.googlecode.tawus.gravatar.services.GravatarURLCreator;

/**
 * This component provides gravatar images support for
 * Tapestry5
 * see {@plainlink http://en.gravatar.com/site/implement Gravatar Developer API}
 * for details
 * 
 */
public class Gravatar {
   
   @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL, allowNull = false)
   private String email;
   
   @Parameter(defaultPrefix = BindingConstants.LITERAL)
   private Integer size;
   
   @Parameter(defaultPrefix = BindingConstants.LITERAL)
   private String _default;
   
   @Parameter(defaultPrefix = BindingConstants.LITERAL)
   private String rating;
   
   @Parameter(defaultPrefix = BindingConstants.LITERAL)
   private String type;
   
   @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "false")
   private boolean forceDefault;
   
   @Inject
   private ComponentResources resources;
   
   @Inject
   private GravatarURLCreator urlCreator;
   
   void beginRender(MarkupWriter writer){
      Map<String, String> parameters = getParams();
      writer.element("img", "src", urlCreator.createAvatarURL(email, type, parameters));
      resources.renderInformalParameters(writer);
      writer.end();
   }
   
   private Map<String, String> getParams(){
      Map<String, String> parameters = new HashMap<String, String>();
      
      if(size != null){
         parameters.put("s", String.valueOf(size));
      }
      
      if(_default != null){
         parameters.put("d", _default);
      }
      
      if(rating != null){
         parameters.put("r", rating);
      }
      
      if(type != null){
         parameters.put("t", type);
      }
      
      if(forceDefault){
         parameters.put("f", "y");
      }
            
      return parameters;
   }

}
