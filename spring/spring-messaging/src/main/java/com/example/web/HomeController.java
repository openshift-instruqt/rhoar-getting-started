package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.MemCache;

@Controller
@RequestMapping("/")
public class HomeController {
    private final MemCache cache;

    public HomeController(MemCache cache) {
        this.cache = cache;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("count", this.cache.getCount());

        // Only the last 5 for UI purposes
        model.addAttribute("messages", this.cache.getMessages());
        return "home";
    }

}