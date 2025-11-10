package com.example.Microservicio.controller;

import com.example.Microservicio.model.Product;
import com.example.Microservicio.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Manejo de los productos", description = "Operaciones para gestionar productos")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "Ver lista de libros disponibles", description = "Obtiene todos los libros almacenados")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener libro por ID", description = "Devuelve un libro según su identificador")
    public Product getBookById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo libro", description = "Crea un nuevo registro de libro")
    public Product createBook(@RequestBody Product product) {
        return productService.saveProduct(product);
    }
 
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un libro existente", description = "Modifica los datos de un libro según su ID")
    public Product updateBook(@PathVariable Long id, @RequestBody Product product) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct != null) {
            existingProduct.setNombre(product.getNombre());
            existingProduct.setPrecio(product.getPrecio());
            existingProduct.setDescripcion(product.getDescripcion());
            return productService.saveProduct(existingProduct);
        }
        return null;
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un libro", description = "Elimina un libro por su identificador")
        public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}



