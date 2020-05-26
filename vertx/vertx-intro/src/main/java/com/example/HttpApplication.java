package com.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;

public class HttpApplication extends AbstractVerticle {

  static final String template = "Hello, %s!";

  @Override
  public void start() {
    
    // TODO: Create a router object
    
    // TODO: Add router for /api/greeting here
    
    // TODO: Add a StaticHandler for accepting incoming requests
    
    // TODO: Create the HTTP server listening on port 8080
    
    System.out.println("THE HTTP APPLICATION HAS STARTED");
  }

  // TODO: Add method for greeting here

  
}
