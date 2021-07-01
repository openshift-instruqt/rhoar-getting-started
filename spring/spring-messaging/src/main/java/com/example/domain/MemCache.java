package com.example.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Service;

@Service
public class MemCache {
    private static final int CACHE_MAX_SIZE = 5;
    private Queue<Fruit> messages = new ArrayDeque<>(CACHE_MAX_SIZE);

    public long getCount() {
        return this.messages.size();
    }

    public void addMessage(Fruit fruit) {
        this.messages.add(fruit);

        if (this.messages.size() > CACHE_MAX_SIZE) {
            this.messages.remove();
        }
    }

    public List<Fruit> getMessages() {
        return new ArrayList<>(this.messages);
    }
}
