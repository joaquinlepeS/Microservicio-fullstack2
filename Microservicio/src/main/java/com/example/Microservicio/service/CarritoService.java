package com.example.Microservicio.service;

import com.example.Microservicio.model.Carrito;
import com.example.Microservicio.model.CarritoItem;
import com.example.Microservicio.model.Product;
import com.example.Microservicio.model.Usuario;
import com.example.Microservicio.repository.CarritoItemRepository;
import com.example.Microservicio.repository.CarritoRepository;
import com.example.Microservicio.repository.ProductRepository;
import com.example.Microservicio.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository itemRepository;
    private final ProductRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public CarritoService(CarritoRepository carritoRepository,
                          CarritoItemRepository itemRepository,
                          ProductRepository productoRepository,
                          UsuarioRepository usuarioRepository) {

        this.carritoRepository = carritoRepository;
        this.itemRepository = itemRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Carrito obtenerCarrito(Long usuarioId) {
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId);

        if (carrito == null) {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

            carrito = new Carrito();
            carrito.setUsuario(usuario);
            carrito.setTotal(0.0);

            carritoRepository.save(carrito);
        }

        return carrito;
    }

    public Carrito agregarProducto(Long usuarioId, Long productoId, Integer cantidad) {

        Carrito carrito = obtenerCarrito(usuarioId);
        Product producto = productoRepository.findById(productoId).orElse(null);

        CarritoItem item = new CarritoItem();
        item.setCarrito(carrito);
        item.setProducto(producto);
        item.setCantidad(cantidad);

        Double subtotal = (double) producto.getPrecio() * cantidad;
        item.setSubtotal(subtotal);

        itemRepository.save(item);

        carrito.setTotal(carrito.getTotal() + subtotal);
        carritoRepository.save(carrito);

        return carrito;
    }

    public Carrito vaciarCarrito(Long usuarioId) {
        Carrito carrito = obtenerCarrito(usuarioId);
        List<CarritoItem> items = carrito.getItems();

        itemRepository.deleteAll(items);
        carrito.setTotal(0.0);

        return carritoRepository.save(carrito);
    }
}
