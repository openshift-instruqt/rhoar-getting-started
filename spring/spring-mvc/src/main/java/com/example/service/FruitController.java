package com.example.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/fruits")
public class FruitController {

    private List<Fruit> fruits = new ArrayList<>();

    @GetMapping
    public String home(Model model) {
        model.addAttribute("fruits", fruits);     // For the List view
        model.addAttribute("fruitForm", new Fruit()); // For the Form
        return "home";
    }

    @PostMapping
    public String createFruit(@ModelAttribute Fruit fruit) {
        if(fruits.stream().noneMatch(f -> f.getName().equalsIgnoreCase(fruit.getName()))) {
            fruits.add(fruit);
        }
        return "redirect:/fruits";
    }
}
