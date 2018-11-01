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

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Circuit Breaker state handler.
 */
public class CircuitBreakerHandler implements WebSocketHandler {

    private Queue<WebSocketSession> currentSessions = new ConcurrentLinkedQueue<>();

    void sendMessage(CircuitBreakerState state) throws Exception {
        TextMessage message = new TextMessage("isOpen:" + CircuitBreakerState.OPEN.equals(state));
        for (WebSocketSession session : currentSessions) {
            session.sendMessage(message);
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
