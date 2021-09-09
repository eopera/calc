package com.filatov.calc.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CalcResource {
    @GetMapping("/add")
    Integer add(@RequestParam int a, @RequestParam int b) {
        return a + b;
    }
}
