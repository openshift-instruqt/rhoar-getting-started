package com.example.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
public class DatabaseTest {
    @Autowired
    private FruitRepository fruitRepository;

    @Test
    public void testGetAll() {
        assertThat(this.fruitRepository.findAll())
                .hasSize(3);
    }

    @Test
    public void getOne() {
        assertThat(this.fruitRepository.findById(1L))
          .isNotNull()
          .isPresent();
    }

    @Test
    public void updateAFruit() {
        Optional<Fruit> appleOptional = this.fruitRepository.findById(2L);

        assertThat(appleOptional)
          .isNotNull()
          .isPresent()
          .get()
          .extracting(Fruit::getName)
          .isEqualTo("Apple");

        Fruit apple = appleOptional.get();
        apple.setName("Green Apple");
        this.fruitRepository.save(apple);

        assertThat(this.fruitRepository.findById(2L))
          .isNotNull()
          .isPresent()
          .get()
          .extracting(Fruit::getName)
          .isEqualTo("Green Apple");
    }

    @Test
    public void createAndDeleteAFruit() {
        Long orangeId = this.fruitRepository.save(new Fruit("Orange")).getId();
        Optional<Fruit> orange = this.fruitRepository.findById(orangeId);

        assertThat(orange)
          .isNotNull()
          .isPresent();

        this.fruitRepository.deleteById(orangeId);

        assertThat(this.fruitRepository.findById(orangeId))
          .isNotNull()
          .isNotPresent();
    }

    @Test
    public void getWrongId() {
        assertThat(this.fruitRepository.findById(9999L))
          .isNotNull()
          .isNotPresent();
    }
}
