package com.googlecode.tawus.gravatar.internal;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.googlecode.tawus.gravatar.services.GravatarURLCreator;

public class GravatarURLCreatorImpl implements GravatarURLCreator{
   @Symbol("gravatar-base-url")
   @Inject
   @Property
   private String baseURL;

   @Override
   public String createAvatarURL(String email, String type, Map<String, String> parameters) {
      StringBuilder sb = new StringBuilder();
      sb.append(baseURL);
      sb.append("gravatar/");
      sb.append(md5(email)); //hash
      if(type != null){
         sb.append(".");
         sb.append(type);
      }
      
      sb.append('?');      
      for(String key: parameters.keySet()){
         sb.append('&');
         sb.append(key);
         sb.append('=');
         sb.append(encode(parameters.get(key)));
      }
      
      return sb.toString();
   }

   private String encode(String string) {
      try {
         return URLEncoder.encode(string, "UTF-8");
      } catch (UnsupportedEncodingException e) {
         throw new RuntimeException("Could not encode : " + string, e);
      }
   }
   
   private String hex(byte[] array) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < array.length; ++i) {
         sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
      }
      return sb.toString();
   }

   private String md5(String message) {
      try {
         MessageDigest md = MessageDigest.getInstance("MD5");
         return hex(md.digest(message.getBytes("CP1252")));
      } catch (NoSuchAlgorithmException e) {
      } catch (UnsupportedEncodingException e) {
      }
      return null;
   }

}
