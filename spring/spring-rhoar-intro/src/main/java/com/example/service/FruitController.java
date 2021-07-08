package com.example.service;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fruits")
public class FruitController {

    private final FruitRepository repository;

    public FruitController(FruitRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Fruit> getAll() {
        return this.repository.findAll();
    }

   @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Fruit post(@RequestBody(required = false) Fruit fruit) {
        verifyCorrectPayload(fruit);

        return this.repository.save(fruit);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Fruit get(@PathVariable("id") Integer id) {
        verifyFruitExists(id);

        return this.repository.findById(id).orElse(null);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Fruit put(@PathVariable("id") Integer id, @RequestBody(required = false) Fruit fruit) {
        verifyFruitExists(id);
        verifyCorrectPayload(fruit);

        fruit.setId(id);
        return this.repository.save(fruit);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        verifyFruitExists(id);

        this.repository.deleteById(id);
    }

    private void verifyFruitExists(Integer id) {
        if (!this.repository.existsById(id)) {
            throw new RuntimeException(String.format("Fruit with id=%d was not found", id));
        }
    }

    private void verifyCorrectPayload(Fruit fruit) {
        if (Objects.isNull(fruit)) {
            throw new RuntimeException("Fruit cannot be null");
        }

        if (!Objects.isNull(fruit.getId())) {
            throw new RuntimeException("Id field must be generated");
        }
    }
}
