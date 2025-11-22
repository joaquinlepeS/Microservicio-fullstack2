package com.example.Microservicio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usuario dueño del carrito
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    @JsonIgnore          // para que no haga bucles al serializar
    private Usuario usuario;

    // Producto agregado
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product producto;

    // Cantidad
    private Integer cantidad;
}
