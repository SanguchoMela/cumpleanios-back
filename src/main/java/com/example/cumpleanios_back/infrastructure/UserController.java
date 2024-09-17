package com.example.cumpleanios_back.infrastructure;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/hello")
    public String helloUnsafe() {
        return "Hello World";
    }

    @GetMapping("/hello-safe")
    public String helloSafe() {
        return "Hello world secured";
    }
}
