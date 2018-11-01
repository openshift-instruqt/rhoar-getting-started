package com.example;

import java.util.Collections;

import com.example.service.Fruit;
import com.example.service.FruitRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Autowired
    private FruitRepository fruitRepository;

    @Before
    public void beforeTest() {
    }

    @Test
    public void testGetAll() {
      assertTrue(fruitRepository.findAll().spliterator().getExactSizeIfKnown()==3);
    }

    @Test
    public void getOne() {
      assertTrue(fruitRepository.findOne(1)!=null);
    }

    @Test
    public void updateAFruit() {
        Fruit apple = fruitRepository.findOne(2);
        assertTrue(apple!=null);
        assertTrue(apple.getName().equals("Apple"));

        apple.setName("Green Apple");
        fruitRepository.save(apple);

        assertTrue(fruitRepository.findOne(2).getName().equals("Green Apple"));
    }

    @Test
    public void createAndDeleteAFruit() {
        int orangeId = fruitRepository.save(new Fruit("Orange")).getId();
        Fruit orange = fruitRepository.findOne(orangeId);
        assertTrue(orange!=null);
        fruitRepository.delete(orange);
        assertTrue(fruitRepository.findOne(orangeId)==null);
    }

    @Test
    public void getWrongId() {
      assertTrue(fruitRepository.findOne(9999)==null);
    }
}