package com.googlecode.tawus.cometd.examples.services;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Configure;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.cometd.java.annotation.Session;
import org.cometd.server.authorizer.GrantAuthorizer;


@Service("helloService")
public class HelloService {
   @SuppressWarnings("unused")
   @Inject
   private BayeuxServer bayeux;
   
   @Session
   private ServerSession serverSession;
   
   @Configure({"/service/hello"})
   public void configure(ConfigurableServerChannel channel){
      channel.setLazy(true);
      channel.addAuthorizer(GrantAuthorizer.GRANT_PUBLISH);
      channel.addAuthorizer(GrantAuthorizer.GRANT_SUBSCRIBE);
   }
   
   @Listener("/service/hello")
   public void echo(ServerSession remote, ServerMessage.Mutable message){
      Map<String, Object> input = message.getDataAsMap();
      String name = (String)input.get("name");
      
      Map<String, Object> output = new HashMap<String, Object>();
      output.put("greeting", "Hello, " + name); 
      remote.deliver(serverSession, "/hello", output, null);
   }
   
}

