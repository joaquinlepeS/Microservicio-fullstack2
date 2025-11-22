package com.example.Microservicio.controller;

import com.example.Microservicio.model.Usuario;
import com.example.Microservicio.security.JwtUtil;
import com.example.Microservicio.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          UsuarioService usuarioService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        // 1. Autenticar usuario con Spring Security
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // 2. Si la autenticación funciona, cargar usuario
        Usuario usuario = usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3. Generar token JWT
        String token = jwtUtil.generateToken((UserDetails) auth.getPrincipal());

        // 4. Devolver datos del usuario + token
        return ResponseEntity.ok(Map.of(
                "token", token,
                "email", usuario.getEmail(),
                "nombre", usuario.getNombre(),
                "rol", usuario.getRol().name()
        ));
    }
}
