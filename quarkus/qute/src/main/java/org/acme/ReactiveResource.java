package org.acme;

import io.quarkus.qute.Template;
import io.quarkus.qute.Location;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
public class ReactiveResource {

    @Inject
    SampleService service;

    @Location("reports/v1/report_01.json.template")
    Template report;

    @Route(path = "/reactive", methods = Route.HttpMethod.GET, produces = MediaType.APPLICATION_JSON)
    void reactive(RoutingExchange ex) throws Exception {
        report
          .data("samples",service.get())
          .data("now", java.time.LocalDateTime.now())
          .renderAsync()
          .thenAccept((val) -> ex.ok(val));
    }
}
