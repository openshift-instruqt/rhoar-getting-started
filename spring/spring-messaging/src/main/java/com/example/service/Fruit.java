package com.example.service;

import java.util.ArrayList;
import java.util.Random;

public class Fruit {

    private String name;

    private static ArrayList<String> fruits = new ArrayList() {{
        add("Apple");
        add("Banana");
        add("Watermelon");
    }};

    private static String getRandomFruit() {
        Random rand = new Random();
        int index = rand.nextInt(fruits.size());
        return fruits.get(index);
    }

    public Fruit() {
        this.name = getRandomFruit();
    }

    public Fruit(String name) {
        this.name = name;
    }

    public String getFruit() {
        return name;
    }

    public void setFruit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Fruit{ Name ='" + name + '\'' + " }";
    }
}
