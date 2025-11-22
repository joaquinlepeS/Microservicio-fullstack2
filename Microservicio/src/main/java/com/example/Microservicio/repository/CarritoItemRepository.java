package com.example.Microservicio.repository;

import com.example.Microservicio.model.CarritoItem;
import com.example.Microservicio.model.Product;
import com.example.Microservicio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {

    List<CarritoItem> findByUsuario(Usuario usuario);

    Optional<CarritoItem> findByUsuarioAndProducto(Usuario usuario, Product producto);

    void deleteAllByUsuario(Usuario usuario);
}
