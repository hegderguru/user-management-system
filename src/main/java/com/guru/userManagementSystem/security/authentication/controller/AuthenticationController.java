package com.guru.userManagementSystem.security.authentication.controller;

import com.guru.userManagementSystem.security.response.UamUserDetails;
import com.guru.userManagementSystem.security.service.UamUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    UamUserDetailsService uamUserDetailsService;

    @GetMapping("authenticate1")
    public ResponseEntity<String> authenticate() {
        return ResponseEntity.ok().body("Success");
    }

    @PostMapping("register")
    public ResponseEntity<UamUserDetails> createUamUser(@RequestBody UamUserDetails uamUserDetails) {
        return ResponseEntity.ok(uamUserDetailsService.createUamUser(uamUserDetails));
    }

    @GetMapping("username/{username}")
    public ResponseEntity<UamUserDetails> fetchUamUserDetails(@PathVariable String username) {
        return ResponseEntity.ok(uamUserDetailsService.loadUserByUsername(username));
    }

}
