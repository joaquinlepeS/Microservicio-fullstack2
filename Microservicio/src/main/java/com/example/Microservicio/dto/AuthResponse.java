package com.example.Microservicio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private Long id;
    private String nombre;
    private String email;
    private String rol;
}
