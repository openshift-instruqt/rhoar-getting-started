package io.openshift.booster;

import static com.jayway.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.json.Json;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.arquillian.cube.openshift.impl.enricher.AwaitRoute;
import org.arquillian.cube.openshift.impl.enricher.RouteURL;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Radek Koubsky
 * @author Ales Justin
 */
@RunWith(Arquillian.class)
public class OpenShiftIT {
    private static final String OK = "ok";
    private static final String FAIL = "fail";
    private static final String CLOSED = "closed";
    private static final String OPEN = "open";
    private static final String HELLO_OK = "Hello, World!";
    private static final String HELLO_FALLBACK = "Hello, Fallback!";

    // See also circuitBreaker.sleepWindowInMilliseconds
    private static final long SLEEP_WINDOW = 5000l;
    // See also circuitBreaker.requestVolumeThreshold
    private static final long REQUEST_THRESHOLD = 3;

    @AwaitRoute(path = "/health")
    @RouteURL("spring-boot-circuit-breaker-name")
    private URL nameBaseUri;

    @AwaitRoute(path = "/health")
    @RouteURL("spring-boot-circuit-breaker-greeting")
    private URL greetingBaseUri;

    @Before
    public void setup() {
        await().pollInterval(1, TimeUnit.SECONDS).atMost(5, TimeUnit.MINUTES).until(() -> {
            try {
                Response gr = RestAssured.get(greetingBaseUri + "api/ping");
                Response nr = RestAssured.get(nameBaseUri + "api/ping");
                return gr.getStatusCode() == 200 && nr.getStatusCode() == 200;
            } catch (Exception ignored) {
                return false;
            }
        });
    }

    @Test
    public void testCircuitBreaker() throws InterruptedException {
        assertCircuitBreaker(CLOSED);
        assertGreeting(HELLO_OK);
        changeNameServiceState(FAIL);
        for (int i = 0; i < REQUEST_THRESHOLD; i++) {
            assertGreeting(HELLO_FALLBACK);
        }
        // Circuit breaker should be open now
        // Wait a little to get the current health counts - see also metrics.healthSnapshot.intervalInMilliseconds
        await().atMost(5, TimeUnit.SECONDS).until(() -> testCircuitBreakerState(OPEN));
        changeNameServiceState(OK);
        // See also circuitBreaker.sleepWindowInMilliseconds
        await().atMost(7, TimeUnit.SECONDS).pollDelay(SLEEP_WINDOW, TimeUnit.MILLISECONDS).until(() -> testGreeting(HELLO_OK));
        // The health counts should be reset
        assertCircuitBreaker(CLOSED);
    }

    private Response greetingResponse() {
        return RestAssured.when().get(greetingBaseUri + "api/greeting");
    }

    private void assertGreeting(String expected) {
        Response response = greetingResponse();
        response.then().statusCode(200).body(containsString(expected));
    }

    private boolean testGreeting(String expected) {
        Response response = greetingResponse();
        response.then().statusCode(200);
        return response.getBody().asString().contains(expected);
    }

    private Response circuitBreakerResponse() {
        return RestAssured.when().get(greetingBaseUri + "api/cb-state");
    }

    private void assertCircuitBreaker(String expectedState) {
        Response response = circuitBreakerResponse();
        response.then().statusCode(200).body("state", equalTo(expectedState));
    }

    private boolean testCircuitBreakerState(String expectedState) {
        Response response = circuitBreakerResponse();
        response.then().statusCode(200);
        return response.getBody().asString().contains(expectedState);
    }

    private void changeNameServiceState(String state) {
        Response response = RestAssured.given().header("Content-type", "application/json")
                .body(Json.createObjectBuilder().add("state", state).build().toString()).put(nameBaseUri + "api/state");
        response.then().assertThat().statusCode(200).body("state", equalTo(state));
    }
}
