package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/")
public class HomeController {

    private MemCache cache;

    @Autowired
    public HomeController(MemCache cache) {
        this.cache = cache;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("count", cache.getCount());

        // Only the last 5 for UI purposes
        model.addAttribute("messages", cache.getMessages());
        return "home";
    }

}
