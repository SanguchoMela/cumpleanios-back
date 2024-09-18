package com.example.cumpleanios_back.infrastructure.controller;

import com.example.cumpleanios_back.application.services.impl.UserDetailServiceImpl;
import com.example.cumpleanios_back.infrastructure.dto.auth.AuthLoginRequest;
import com.example.cumpleanios_back.infrastructure.dto.auth.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }
}
