package org.acme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/taster")
public class TasterController {

    private final FruitRepository fruitRepository;

    private final TasterBean tasterBean;

    public TasterController(FruitRepository fruitRepository, TasterBean tasterBean) {
        this.fruitRepository = fruitRepository;
        this.tasterBean = tasterBean;
    }

    @GetMapping(produces = "application/json")
    public List<TasteResult> tasteAll() {
        List<TasteResult> result = new ArrayList<>();

        fruitRepository.findAll().forEach(fruit -> {
            result.add(new TasteResult(fruit, tasterBean.taste(fruit.getName())));
        });
        return result;
    }

    @GetMapping(path = "/{color}", produces = "application/json")
    public List<TasteResult> tasteByColor(@PathVariable(name = "color") String color) {
        List<TasteResult> result = new ArrayList<>();
        fruitRepository.findByColor(color).forEach(fruit -> {
            result.add(new TasteResult(fruit, tasterBean.taste(fruit.getName())));
        });
        return result;
    }

    public class TasteResult {
        public Fruit fruit;
        public String result;

        public TasteResult(Fruit fruit, String result) {
            this.fruit = fruit;
            this.result = result;
        }

    }
}
