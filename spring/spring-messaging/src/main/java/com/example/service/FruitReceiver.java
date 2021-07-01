package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.example.domain.Fruit;
import com.example.domain.MemCache;

@Component
public class FruitReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(FruitReceiver.class);
    private final MemCache cache;

    public FruitReceiver(MemCache cache) {
        this.cache = cache;
    }

    @JmsListener(destination = "${queue.name}")
    public void receiveMessage(Fruit fruit) {
        LOGGER.info("Received: {}", fruit);
        this.cache.addMessage(fruit);
    }
}
