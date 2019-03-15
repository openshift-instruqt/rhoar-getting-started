package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;

import java.util.List;

@RestController
@RequestMapping("/")
public class FruitController {
	
	  @RequestMapping("/")
	    String index() {
	        return "redirect:/fruits";
	    }
	

	@Autowired
	FruitRepository repository;

	@GetMapping
	public List<Fruit> getAll() {
		return repository.findAll();
	}
	
	 @GetMapping("/{id}")
	    public Fruit getFruit(@PathVariable("id") Long id) {
	        return repository.findById(id).orElse(null);
	    }
}
