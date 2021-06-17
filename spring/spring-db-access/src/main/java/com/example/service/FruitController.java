package com.example.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/api/fruits")
public class FruitController {
    private final FruitRepository repository;

    public FruitController(FruitRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ResponseBody
    public Iterable<Fruit> getAll() {
        return this.repository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createFruit(@RequestBody(required = false) Fruit fruit) {
        this.repository.save(fruit);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public Fruit getFruit(@PathVariable("id") Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Fruit updateFruit(@PathVariable("id") Long id, @RequestBody(required = false) Fruit fruit) {
        fruit.setId(id);
        return this.repository.save(fruit);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.repository.deleteById(id);
    }

}
