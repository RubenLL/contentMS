package com.poc.ruben.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class getImage {
    @GetMapping("/image")
    public String getImage() {
        return "getImage";
    }
}
