package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.domain.Fruit;

@Component
public class FruitPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(FruitPublisher.class);
    private final JmsTemplate jmsTemplate;

    @Value("${queue.name}")
    private String queueName;

    public FruitPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Scheduled(fixedRate = 3000L)
    public void sendTick() {
        Fruit fruit = new Fruit();
        LOGGER.info("Publishing fruit {} to destination {}", fruit, this.queueName);
        this.jmsTemplate.convertAndSend(this.queueName, fruit);
    }

}
