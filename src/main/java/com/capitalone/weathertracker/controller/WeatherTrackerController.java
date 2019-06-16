package com.capitalone.weathertracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Top controller to allow default path
 */
@Controller
public class WeatherTrackerController {

    @GetMapping({"/*","/hello","/index"})
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }
}
