package com.googlecode.tawus.cometd.services;

import java.util.List;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.server.BayeuxServerImpl;
import org.cometd.server.transport.HttpTransport;

public interface BayeuxServerSource {
   
   BayeuxServer getBayeux();
   
   BayeuxServerImpl getBayeuxImpl();
   
   List<HttpTransport> getAllowedTransports();

   void start();
}
