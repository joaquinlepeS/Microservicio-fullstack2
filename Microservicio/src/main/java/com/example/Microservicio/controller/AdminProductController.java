package com.example.Microservicio.controller;

import com.example.Microservicio.model.Product;
import com.example.Microservicio.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/productos")
@CrossOrigin(origins = "*")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    // 🔐 ADMIN: Crear producto
    @PostMapping
    public ResponseEntity<Product> crear(@RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.save(product));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 🔐 ADMIN: Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Product> actualizar(@PathVariable Long id, @RequestBody Product product) {
        Product actualizado = productService.update(id, product);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    // 🔐 ADMIN: Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = productService.delete(id);
        if (!eliminado) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
