package com.googlecode.tawus.cometd.examples;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.ClientTransport;
import org.cometd.client.transport.LongPollingTransport;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class JavaClientBasedTest extends Assert {
   private Server server;
   private static final Logger logger = LoggerFactory.getLogger(JavaClientBasedTest.class);
   private String greeting;

   @BeforeClass
   public void startupServer() throws Exception {
      server = new Server(9990);
      server.setStopAtShutdown(true);
      server.setGracefulShutdown(1000);

      WebAppContext context = new WebAppContext();

      context.setContextPath("/test");
      context.setWar("src/test/webapp");

      server.setHandler(context);
      server.start();
      logger.info("Server started");
   }

   @Test
   public void testJavaClient() throws Exception {
      HttpClient httpClient = new HttpClient();
      httpClient.setConnectTimeout(100000);
      httpClient.start();
      Map<String, Object> options = new HashMap<String, Object>();

      ClientTransport transport = LongPollingTransport.create(options,
            httpClient);
      final BayeuxClient client = new BayeuxClient(
            "http://localhost:9990/test/cometd/", transport);
      client.handshake();
      assertTrue(client.waitFor(1000, BayeuxClient.State.CONNECTED));

      assertNull(greeting);
      client.batch(new Runnable() {

         public void run() {
            logger.info("Subscribing to /hello");

            // /Subscribe
            client.getChannel("/hello").subscribe(
                  new ClientSessionChannel.MessageListener() {

                     public void onMessage(ClientSessionChannel channel,
                           Message message) {
                        logger.info("Got Subscribed message");
                        greeting = (String) message.getDataAsMap().get(
                              "greeting");
                     }
                  });

            logger.info("Subscribed, now publishing");
            
            // Publish
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("name", "Tawus");
            client.getChannel("/service/hello").publish(data);

         }

      });

      Thread.sleep(4000);
      assertEquals(greeting, "Hello, Tawus");

      // Disconnect
      client.disconnect();
      client.waitFor(2000, BayeuxClient.State.DISCONNECTED);
   }

   @AfterClass
   public void stopServer() throws Exception {
      server.stop();
      logger.info("Server stopped");
   }
}
