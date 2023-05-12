package com.demo.hotel.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){

        return ResponseEntity.ok("ping");
    }
}
