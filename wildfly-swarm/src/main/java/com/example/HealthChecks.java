package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.wildfly.swarm.health.Health;
import org.wildfly.swarm.health.HealthStatus;

@Path("/service")
public class HealthChecks {

    @GET
    @Health
    @Path("/health")
    public HealthStatus check() {

        if (ApplicationConfig.IS_ALIVE.get()) {
            return HealthStatus.named("server-state").up();
        } else {
            return HealthStatus.named("server-state").down();
        }
    }
}

