package com.example.Microservicio.repository;

import com.example.Microservicio.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Carrito findByUsuarioId(Long usuarioId);
}
