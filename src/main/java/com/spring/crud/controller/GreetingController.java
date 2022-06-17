package com.spring.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    @GetMapping
    public ResponseEntity<?> hello(){
        return ResponseEntity.ok( Collections.singletonMap("message", "Hello") );
    }

}
