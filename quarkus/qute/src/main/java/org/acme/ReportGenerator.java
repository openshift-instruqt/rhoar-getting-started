package org.acme;

import java.io.FileWriter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import io.quarkus.qute.Template;
import io.quarkus.qute.Location;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class ReportGenerator {

    @Inject
    SampleService service;

    private FileWriter fout = null;

    @Location("reports/v1/report_01.json.template")
    Template report;

    @Scheduled(cron="* * * ? * *")
    void generate() throws Exception {
        String result = report
            .data("samples", service.get())
            .data("now", java.time.LocalDateTime.now())
            .render();
            System.out.println("report: " + result);
        if (fout != null) {
            fout.write(result + "\n");
            fout.flush();
        }

    }

    void onStart(@Observes StartupEvent ev) throws Exception {
        fout = new FileWriter("/tmp/report.json", true);
    }
    void onShutdown(@Observes ShutdownEvent ev) throws Exception {
        fout.close();
        fout = null;
    }
}
