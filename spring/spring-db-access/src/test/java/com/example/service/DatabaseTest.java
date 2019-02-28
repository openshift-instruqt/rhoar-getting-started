package com.example.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class DatabaseTest {

    @Autowired
    private FruitRepository fruitRepository;

    @Test
    public void testGetAll() {
        assertTrue(fruitRepository.findAll().spliterator().getExactSizeIfKnown() == 3);
    }

    @Test
    public void getOne() {
        assertNotNull(fruitRepository.findById(1L).orElse(null));
    }

    @Test
    public void updateAFruit() {
        Fruit apple = fruitRepository.findById(2L).orElse(null);
        assertNotNull(apple);
        assertEquals("Apple", apple.getName());

        apple.setName("Green Apple");
        fruitRepository.save(apple);

        assertEquals("Green Apple", fruitRepository.findById(2L).orElse(null).getName());
    }

    @Test
    public void createAndDeleteAFruit() {
        Long orangeId = fruitRepository.save(new Fruit("Orange")).getId();
        Fruit orange = fruitRepository.findById(orangeId).orElse(null);
        assertNotNull(orange);

        fruitRepository.deleteById(orangeId);
        assertNull(fruitRepository.findById(orangeId).orElse(null));
    }

    @Test
    public void getWrongId() {
        assertNull(fruitRepository.findById(9999L).orElse(null));
    }
}