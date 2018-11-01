package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private MemCache cache;

    @Autowired
    public Receiver(MemCache cache) {
        this.cache = cache;
    }

    @JmsListener(destination = "${queue.boot}")
    public void receiveMessage(Fruit fruit) {
        System.out.println("Received: " + fruit);
        cache.incr();
        cache.addMessage(fruit);
    }

}
