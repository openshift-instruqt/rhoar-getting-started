/*
 * Copyright 2016-2017 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.openshift.booster;

import static com.jayway.awaitility.Awaitility.await;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.Is.is;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.jayway.restassured.response.Response;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.internal.readiness.Readiness;
import io.fabric8.openshift.client.OpenShiftClient;
import org.arquillian.cube.kubernetes.api.Session;
import org.arquillian.cube.openshift.impl.enricher.AwaitRoute;
import org.arquillian.cube.openshift.impl.enricher.RouteURL;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Arquillian.class)
public class OpenShiftIT {
    private static final String CONFIG_MAP_NAME = "app-config";
    private static final String GREETING_NAME = "spring-boot-configmap-greeting";

    @ArquillianResource
    private OpenShiftClient oc;

    @ArquillianResource
    private Session session;

    @AwaitRoute(path = "/api/greeting")
    @RouteURL(GREETING_NAME)
    private URL greetingServiceBase;

    private String greetingServiceURI;

    @Before
    public void setup() throws Exception {
        greetingServiceURI = greetingServiceBase + "api/greeting";
        waitForApp();
    }

    @Test
    public void testAGreetingEndpoint() {
        verifyEndpoint("Hello");
    }

    @Test
    public void testBGreetingEndpointWithNameParameter() {
        given().param("name", "John")
                .when()
                .get(greetingServiceURI)
                .then()
                .statusCode(200)
                .body("content", is("Hello John from a ConfigMap!"));
    }

    @Test
    public void testCConfigMapUpdate() {
        verifyEndpoint("Hello");
        updateConfigMap();
        rolloutChanges();
        waitForApp();
        verifyEndpoint("Bonjour");
    }

    @Test
    public void testDConfigMapNotPresent() {
        verifyEndpoint("Bonjour");
        deleteConfigMap();
        rolloutChanges();
        await().atMost(5, TimeUnit.MINUTES)
                .catchUncaughtExceptions()
                .until(() ->
                        get(greetingServiceURI).then()
                                .statusCode(500)
                );
    }

    private void verifyEndpoint(final String greeting) {
        when().get(greetingServiceURI)
                .then()
                .statusCode(200)
                .body("content", is(String.format("%s World from a ConfigMap!", greeting)));
    }

    private void updateConfigMap() {
        oc
          .configMaps()
          .withName(CONFIG_MAP_NAME)
          .edit()
          .addToData("application.properties", "greeting.message: Bonjour %s from a ConfigMap!")
          .done();
    }

    private void deleteConfigMap() {
        oc
          .configMaps()
          .withName(CONFIG_MAP_NAME)
          .delete();
    }

    private void rolloutChanges() {
        scale(0);
        scale(1);
    }

    private void scale(final int replicas) {
        oc
          .deploymentConfigs()
          .inNamespace(session.getNamespace())
          .withName(GREETING_NAME)
          .scale(replicas);

        await().atMost(5, TimeUnit.MINUTES)
                .until(() -> {
                    // ideally, we'd look at deployment config's status.availableReplicas field,
                    // but that's only available since OpenShift 3.5
                  List<Pod> pods = oc
                            .pods()
                    .inNamespace(session.getNamespace())
                            .withLabel("deploymentconfig", GREETING_NAME)
                            .list()
                            .getItems();
                    return pods.size() == replicas && pods.stream()
                            .allMatch(Readiness::isPodReady);
                });
    }

  private void waitForApp() {
        await().atMost(5, TimeUnit.MINUTES)
                .until(() -> {
                    try {
                        final Response response = get(greetingServiceURI);
                        return response.getStatusCode() == 200;
                    } catch (final Exception e) {
                        return false;
                    }
                });
    }

}
