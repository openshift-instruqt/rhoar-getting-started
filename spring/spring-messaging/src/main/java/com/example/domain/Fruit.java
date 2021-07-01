package com.example.domain;

import java.util.List;
import java.util.Random;

public class Fruit {
    private String name;
    private static final List<String> FRUITS = List.of("Apple", "Banana", "Watermelon");

    private static String getRandomFruit() {
        Random rand = new Random();
        int index = rand.nextInt(FRUITS.size());
        return FRUITS.get(index);
    }

    public Fruit() {
        this.name = getRandomFruit();
    }

    public Fruit(String name) {
        this.name = name;
    }

    public String getFruit() {
        return this.name;
    }

    public void setFruit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Fruit{ Name ='" + this.name + '\'' + " }";
    }
}
