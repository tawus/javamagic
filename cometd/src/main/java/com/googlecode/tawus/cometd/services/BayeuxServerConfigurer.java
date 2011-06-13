package com.googlecode.tawus.cometd.services;

import java.util.List;

import org.cometd.server.BayeuxServerImpl;

public interface BayeuxServerConfigurer {
   void configure(BayeuxServerImpl bayeuxServerImpl, List<Class<?>> annotatedServices);
}
