package com.ai.analyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
                "status", "success",
                "data", Map.of(
                        "message", "Welcome to the AI Code Analyzer!",
                        "version", "1.0.0"
                )
        );
    }
}
