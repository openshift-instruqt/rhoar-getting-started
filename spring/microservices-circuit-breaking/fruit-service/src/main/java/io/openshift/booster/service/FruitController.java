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

package io.openshift.booster.service;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketHandler;

/**
 * Fruit service controller.
 */
@RestController
public class FruitController {

    private final NameService nameService;
    private final CircuitBreakerHandler handler = new CircuitBreakerHandler();

    public FruitController(NameService nameService) {
        this.nameService = nameService;
    }

    @RequestMapping("/api/ping")
    public Fruit getPing() throws Exception {
        return new Fruit("OK");
    }

    /**
     * Endpoint to get a greeting. This endpoint uses a name server to get a name for the greeting.
     * <p>
     * Request to the name service is guarded with a circuit breaker. Therefore if a name service is not available or is too
     * slow to response fallback name is used.
     *
     * @return Fruit string.
     */
    @RequestMapping("/api/greeting")
    public Fruit getFruit() throws Exception {
        String result = String.format("You've picked %s!", nameService.getName());
        handler.sendMessage(nameService.getState());
        return new Fruit(result);
    }

    @Bean
    public WebSocketHandler getHandler() {
        return handler;
    }

    static class Fruit {
        private final String content;

        public Fruit(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}
