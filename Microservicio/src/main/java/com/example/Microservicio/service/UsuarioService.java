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

    public Usuario registrar(Usuario usuario) {

    if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
        throw new IllegalArgumentException("Email duplicado");
    }

    if (usuario.getRol() == null) {
        usuario.setRol(Rol.CLIENTE);
    }

    return usuarioRepository.save(usuario);
}

    
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    public Optional<Usuario> buscarPorEmail(String email) {
    return usuarioRepository.findByEmail(email);
}

}
