/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.openshift.booster;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

import io.openshift.booster.service.MessageProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoosterApplicationTest {

    protected static final String GREETING_PATH = "api/greeting";
    @Value("${local.server.port}")
    private int port;

    @Autowired
    private MessageProperties properties;

    @Test
    public void testGreetingEndpoint() {
        given()
            .baseUri(String.format("http://localhost:%d", port))
            .get(GREETING_PATH)
            .then()
            .statusCode(200)
            .body("content", is(String.format(properties.getMessage(), "Banana")));
    }

    @Test
    public void testGreetingEndpointWithNameParameter() {
        given()
            .baseUri(String.format("http://localhost:%d", port))
            .param("name", "John")
            .get(GREETING_PATH)
            .then()
            .statusCode(200)
            .body("content", is(String.format(properties.getMessage(), "John")));
    }

}