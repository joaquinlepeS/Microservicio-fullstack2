package com.example.Microservicio.service;

import com.example.Microservicio.model.Product;
import com.example.Microservicio.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(Product product) {

        if (product.getNombre() == null || product.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }

        if (product.getDescripcion() == null || product.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }

        if (product.getPrecio() == null) {
            throw new IllegalArgumentException("El precio no puede ser nulo");
        }

        if (product.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        Product existente = productRepository.findById(id).orElse(null);

        if (existente == null) {
            return null;
        }

        if (product.getNombre() != null && !product.getNombre().isEmpty()) {
            existente.setNombre(product.getNombre());
        }

        if (product.getDescripcion() != null && !product.getDescripcion().isEmpty()) {
            existente.setDescripcion(product.getDescripcion());
        }

        if (product.getPrecio() != null && product.getPrecio() >= 0) {
            existente.setPrecio(product.getPrecio());
        }

        return productRepository.save(existente);
    }

    public boolean delete(Long id) {
        Product existente = productRepository.findById(id).orElse(null);

        if (existente == null) {
            return false;
        }

        productRepository.deleteById(id);
        return true;
    }
}
