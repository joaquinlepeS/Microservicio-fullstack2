package com.example.Microservicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Microservicio.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}