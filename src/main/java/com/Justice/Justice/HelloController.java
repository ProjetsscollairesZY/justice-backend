package com.Justice.Justice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "🚀 Backend Justice connecté avec MongoDB Atlas !";
    }
    
    @GetMapping("/test")
    public String test() {
        return " API Justice fonctionne sur le port 8081 !";
    }
}