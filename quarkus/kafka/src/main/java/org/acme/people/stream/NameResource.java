package org.acme.people.stream;

import io.smallrye.reactive.messaging.annotations.Stream;

import org.reactivestreams.Publisher;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * A simple resource retrieving the in-memory "my-data-stream" and sending the items as server-sent events.
 */
@Path("/names")
public class NameResource {

    @Inject
    @Stream("my-data-stream") Publisher<String> names;  

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)              
    public Publisher<String> stream() {                  
        return names;
    }
}
