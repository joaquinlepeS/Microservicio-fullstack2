package com.example.Microservicio.service;

import com.example.Microservicio.model.CarritoItem;
import com.example.Microservicio.model.Product;
import com.example.Microservicio.model.Usuario;
import com.example.Microservicio.repository.CarritoItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoItemRepository carritoRepo;
    private final ProductService productService;

    public CarritoService(CarritoItemRepository carritoRepo,
                          ProductService productService) {
        this.carritoRepo = carritoRepo;
        this.productService = productService;
    }

    public List<CarritoItem> listarPorUsuario(Usuario usuario) {
        return carritoRepo.findByUsuario(usuario);
    }

    public CarritoItem agregarProducto(Usuario usuario, Long productoId, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            cantidad = 1;
        }

        Product producto = productService.findById(productoId);
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        CarritoItem item = carritoRepo
                .findByUsuarioAndProducto(usuario, producto)
                .orElse(null);

        if (item == null) {
            item = new CarritoItem();
            item.setUsuario(usuario);
            item.setProducto(producto);
            item.setCantidad(cantidad);
        } else {
            item.setCantidad(item.getCantidad() + cantidad);
        }

        return carritoRepo.save(item);
    }

    public CarritoItem actualizarCantidad(Usuario usuario, Long itemId, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        CarritoItem item = carritoRepo.findById(itemId).orElse(null);
        if (item == null || !item.getUsuario().getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("Item no encontrado para este usuario");
        }

        item.setCantidad(cantidad);
        return carritoRepo.save(item);
    }

    public void eliminarItem(Usuario usuario, Long itemId) {
        CarritoItem item = carritoRepo.findById(itemId).orElse(null);
        if (item == null || !item.getUsuario().getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("Item no encontrado para este usuario");
        }
        carritoRepo.delete(item);
    }

    public void vaciarCarrito(Usuario usuario) {
        carritoRepo.deleteAllByUsuario(usuario);
    }
}
