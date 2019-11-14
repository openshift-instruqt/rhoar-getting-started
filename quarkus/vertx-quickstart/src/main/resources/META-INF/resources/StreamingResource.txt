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



@Path("/geo")
public class StreamingResource {

    // TODO streaming0


    double pointsY[] = { -77.0369, -77.0502, -77.0353, -77.0424, -77.1424, -77.0365 };

    double pointsX[] = { 38.9072, 38.8893, 38.8895, 38.8829, 38.9829, 38.8977 };


    // TODO streaming1

    public int getRandom(){
        return (int)(Math.random() * 6) ;
    }

    public String getRandomLocation(){
        return pointsY[getRandom()]+","+pointsX[getRandom()];
    }

}
