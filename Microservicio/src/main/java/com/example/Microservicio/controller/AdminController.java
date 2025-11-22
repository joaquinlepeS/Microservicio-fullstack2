package com.example.Microservicio.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping("/test")
    public String testAdmin() {
        return "Ruta ADMIN funcionando";
    }
}
