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

    // 🔵 PUBLICO: obtener todos
    @GetMapping
    public List<Product> obtenerTodos() {
        return productService.findAll();
    }

    // 🔵 PUBLICO: obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> obtenerPorId(@PathVariable Long id) {
        Product p = productService.findById(id);
        if (p == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    // 🔴 SOLO ADMIN: crear producto
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Product product) {
        try {
            Product creado = productService.save(product);
            return ResponseEntity.ok(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 🔴 SOLO ADMIN: actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Long id, @RequestBody Product product) {
        Product actualizado = productService.update(id, product);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    // 🔴 SOLO ADMIN: eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
        boolean eliminado = productService.delete(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
