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
        assertNotNull(fruitRepository.findOne(1L));
    }

    @Test
    public void updateAFruit() {
        Fruit apple = fruitRepository.findOne(2L);
        assertNotNull(apple);
        assertEquals("Apple", apple.getName());

        apple.setName("Green Apple");
        fruitRepository.save(apple);

        assertEquals("Green Apple", fruitRepository.findOne(2L).getName());
    }

    @Test
    public void createAndDeleteAFruit() {
        Long orangeId = fruitRepository.save(new Fruit("Orange")).getId();
        Fruit orange = fruitRepository.findOne(orangeId);
        assertNotNull(orange);

        fruitRepository.delete(orange);
        assertNull(fruitRepository.findOne(orangeId));
    }

    @Test
    public void getWrongId() {
        assertNull(fruitRepository.findOne(9999L));
    }
}
