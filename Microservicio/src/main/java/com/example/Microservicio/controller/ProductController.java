package com.example.Microservicio.controller;

import com.example.Microservicio.model.Product;
import com.example.Microservicio.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 🔓 PUBLICO: Todos pueden ver productos (sin JWT)
    @GetMapping
    public List<Product> obtenerTodos() {
        return productService.findAll();
    }

    // 🔓 PUBLICO: Ver detalles de un producto
    @GetMapping("/{id}")
    public ResponseEntity<Product> obtenerPorId(@PathVariable Long id) {
        Product p = productService.findById(id);
        if (p == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }
}
