package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FruitController {

    private JmsTemplate jmsTemplate;

    @Value("${queue.boot}")
    private String queue;

    @Autowired
    public FruitController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Scheduled(fixedRate = 3000L)
    public void sendTick() {
        jmsTemplate.convertAndSend(queue, new Fruit());
    }

}
