package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.service.Fruit;
import com.example.service.FruitRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ApplicationTest {

    @Autowired
    private FruitRepository fruitRepository;

    @Test
    public void testGetAll() {
        assertThat(this.fruitRepository.findAll())
          .isNotNull()
          .hasSize(3);
    }

    @Test
    public void getOne() {
        assertThat(this.fruitRepository.findById(1))
          .isNotNull()
          .isPresent();
    }

    @Test
    public void updateAFruit() {
        Optional<Fruit> apple = this.fruitRepository.findById(2);

        assertThat(apple)
          .isNotNull()
          .isPresent()
          .get()
          .extracting(Fruit::getName)
          .isEqualTo("Apple");

        Fruit theApple = apple.get();
        theApple.setName("Green Apple");
        this.fruitRepository.save(theApple);

        assertThat(this.fruitRepository.findById(2))
          .isNotNull()
          .isPresent()
          .get()
          .extracting(Fruit::getName)
          .isEqualTo("Green Apple");
    }

    @Test
    public void createAndDeleteAFruit() {
        int orangeId = this.fruitRepository.save(new Fruit("Orange")).getId();
        Optional<Fruit> orange = this.fruitRepository.findById(orangeId);
        assertThat(orange)
          .isNotNull()
          .isPresent();

        this.fruitRepository.delete(orange.get());

        assertThat(this.fruitRepository.findById(orangeId))
          .isNotNull()
          .isNotPresent();
    }

    @Test
    public void getWrongId() {
        assertThat(this.fruitRepository.findById(9999))
          .isNotNull()
          .isNotPresent();
    }
}
