package com.guru.userManagementSystem.security.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @GetMapping("authenticate")
    public ResponseEntity<String> authenticate() {
        return ResponseEntity.ok().body("Success");
    }

}
