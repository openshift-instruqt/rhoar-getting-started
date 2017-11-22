package io.openshift.booster;

import com.jayway.restassured.RestAssured;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.openshift.client.OpenShiftClient;
import org.arquillian.cube.kubernetes.api.Session;
import org.arquillian.cube.openshift.impl.enricher.AwaitRoute;
import org.arquillian.cube.openshift.impl.enricher.RouteURL;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.jayway.awaitility.Awaitility.await;
import static com.jayway.restassured.RestAssured.get;
import static io.openshift.booster.HttpApplication.template;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Arquillian.class)
public class OpenShiftIT {
    private String project;

    private final String applicationName = System.getProperty("app.name");

    @ArquillianResource
    private OpenShiftClient client;

    @ArquillianResource
    private Session session;

    @RouteURL("${app.name}")
    @AwaitRoute
    private URL route;

    @Before
    public void setup() {
        RestAssured.baseURI = route.toString();
        project = this.client.getNamespace();
    }

    @Test
    public void testThatWeAreReady() throws Exception {
        await().atMost(5, TimeUnit.MINUTES).until(() -> {
                List<Pod> list = client.pods().inNamespace(project).list().getItems();
                return list.stream()
                    .filter(pod -> pod.getMetadata().getName().startsWith(applicationName))
                    .filter(this::isRunning)
                    .collect(Collectors.toList()).size() >= 1;
            }
        );
        // Check that the route is served.
        await().atMost(5, TimeUnit.MINUTES).catchUncaughtExceptions().until(() -> get().getStatusCode() < 500);
        await().atMost(5, TimeUnit.MINUTES).catchUncaughtExceptions().until(() -> get("/api/greeting")
            .getStatusCode() < 500);
    }

    @Test
    public void testThatWeServeAsExpected() throws MalformedURLException {
        get("/api/greeting").then().body("content", equalTo(String.format(template, "World")));
        get("/api/greeting?name=vert.x").then().body("content", equalTo(String.format(template, "vert.x")));
    }

    private boolean isRunning(Pod pod) {
        return "running".equalsIgnoreCase(pod.getStatus().getPhase());
    }
}
