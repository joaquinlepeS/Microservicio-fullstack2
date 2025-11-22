package com.example.Microservicio.controller;

import com.example.Microservicio.model.Usuario;
import com.example.Microservicio.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Constructor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ✅ GET para probar que el controller responde
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.findAll();
    }

    // ✅ ESTE ES EL ENDPOINT DE REGISTRO
    @PostMapping("/registrar")   // 👈 IMPORTANTE: /registrar
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        try {
            Usuario creado = usuarioService.registrar(usuario);
            return ResponseEntity.ok(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
