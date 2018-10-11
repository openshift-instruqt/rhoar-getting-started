package com.example.service;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FruitRepository {
    public List<Fruit> findAll() {
        return null;
    }

    public Fruit save(Fruit fruit) {

        return fruit;
    }

    public Fruit findOne(Long id) {
        return null;
    }

    public void delete(Long id) {

    }
}
