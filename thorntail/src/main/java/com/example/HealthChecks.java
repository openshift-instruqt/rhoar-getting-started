package com.example;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Health
@ApplicationScoped
public class HealthChecks implements HealthCheck {

    public HealthCheckResponse call() {

        if (ApplicationConfig.IS_ALIVE.get()) {
            return HealthCheckResponse.named("server-state").up().build();
        } else {
            return HealthCheckResponse.named("server-state").down().build();
        }
    }
}