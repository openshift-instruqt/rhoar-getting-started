package com.example.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MemCache {

    private AtomicLong counter = new AtomicLong(0);
    private Queue<Fruit> messages = new ArrayDeque<>();

    public long getCount() {
        return counter.get();
    }

    public void incr() {
        counter.incrementAndGet();
    }

    public void addMessage(Fruit fruit) {
        this.messages.add(fruit);

        if(messages.size() > 5) {
            this.messages.remove();
        }
    }

    public List<Fruit> getMessages() {
        return new ArrayList<>(messages);
    }
}
