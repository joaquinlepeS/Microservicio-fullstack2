package com.example.Microservicio.service;

import com.example.Microservicio.model.Rol;
import com.example.Microservicio.model.Usuario;
import com.example.Microservicio.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // 🔹 Registrar usuario
    public Usuario registrar(Usuario usuario) {

        // ✔ Validación email único
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        // ✔ Rol por defecto
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.CLIENTE);
        }

        return usuarioRepository.save(usuario);
    }

    // 🔹 Listar todos
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // 🔹 Buscar por ID
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // 🔹 Buscar por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
