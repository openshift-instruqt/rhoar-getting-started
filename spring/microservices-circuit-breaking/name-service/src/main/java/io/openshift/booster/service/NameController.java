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

import java.io.IOException;
import java.time.LocalTime;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Name service controller.
 */
@RestController
public class NameController {

    private static final Logger LOG = LoggerFactory.getLogger(NameController.class);

    private static final String theName = "apple";

    private final AtomicBoolean doFail = new AtomicBoolean();
    private final NameServiceWebSockerHandler handler = new NameServiceWebSockerHandler();

    @RequestMapping("/api/ping")
    public StateInfo getPing() throws Exception {
        return StateInfo.OK;
    }

    /**
     * Endpoint to get a name.
     *
     * @return Host name.
     */
    @RequestMapping("/api/name")
    public ResponseEntity<String> getName() throws IOException {
        handler.sendMessage("GET /api/name at " + LocalTime.now());

        if (doFail.get()) {
            return new ResponseEntity<>("Name service down", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            LOG.info(String.format("Returning a name '%s'", theName));
            return new ResponseEntity<>(theName, HttpStatus.OK);
        }
    }

    /**
     * Set name service state - via GET.
     */
    @GetMapping("/api/state")
    public StateInfo applyState(@RequestParam(name = "state") String state) throws Exception {
        doFail.set("fail".equalsIgnoreCase(state));
        LOG.info("Name service state set to " + state);
        handler.sendMessage("state:" + !doFail.get());
        return getState();
    }

    /**
     * Set name service state - via PUT.
     */
    @PutMapping("/api/state")
    public StateInfo applyState(@RequestBody StateInfo info) throws Exception {
        return applyState(info.getState());
    }

    /**
     * Set name service state.
     */
    @RequestMapping("/api/info")
    public StateInfo getState() throws Exception {
        return doFail.get() ? StateInfo.FAIL : StateInfo.OK;
    }

    static class StateInfo {
        private String state;

        static final StateInfo OK = new StateInfo("ok");
        static final StateInfo FAIL = new StateInfo("fail");

        public StateInfo() {
        }

        public StateInfo(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    @Bean
    public WebSocketHandler getHandler() {
        return handler;
    }

    @Bean
    public Filter getCorsFilter() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(CorsConfiguration.ALL);
        configuration.addAllowedMethod(CorsConfiguration.ALL);
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        return new CorsFilter(request -> configuration);
    }

    private class NameServiceWebSockerHandler implements WebSocketHandler {
        private Queue<WebSocketSession> currentSessions = new ConcurrentLinkedQueue<>();

        void sendMessage(String message) throws IOException {
            TextMessage textMessage = new TextMessage(message);
            for (WebSocketSession session : currentSessions) {
                session.sendMessage(textMessage);
            }
        }

        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            currentSessions.add(session);
        }

        public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {
        }

        public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {

        }

        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            currentSessions.remove(session);
        }

        public boolean supportsPartialMessages() {
            return false;
        }
    }
}
