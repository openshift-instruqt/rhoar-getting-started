package com.example.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FruitRepository {
    public List<Fruit> findAll() {
        return null;
    }

    public Fruit save(Fruit fruit) {

        return fruit;
    }

    public Optional<Fruit> findById(Long id) {
        return null;
    }

    public void deleteById(Long id) {

    }
}
