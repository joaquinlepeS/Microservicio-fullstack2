package com.example.Microservicio.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Product producto;

    private Integer cantidad;
    private Double subtotal;
}
