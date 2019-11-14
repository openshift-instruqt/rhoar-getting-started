package org.acme.reactive;

import io.vertx.axle.core.Vertx;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

import java.util.concurrent.CompletionStage;
import io.vertx.core.json.JsonObject;

@Path("/geo")
public class GeoJsonResource{
    @Inject
    Vertx vertx;

        // TODO INJECT_CODE 
}