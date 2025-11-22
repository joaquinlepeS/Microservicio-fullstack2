package com.example.Microservicio.controller;

import com.example.Microservicio.model.Carrito;
import com.example.Microservicio.service.CarritoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/{usuarioId}")
    public Carrito obtenerCarrito(@PathVariable Long usuarioId) {
        return carritoService.obtenerCarrito(usuarioId);
    }

    @PostMapping("/agregar")
    public Carrito agregarProducto(@RequestParam Long usuarioId,
                                   @RequestParam Long productoId,
                                   @RequestParam Integer cantidad) {
        return carritoService.agregarProducto(usuarioId, productoId, cantidad);
    }

    @DeleteMapping("/{usuarioId}/vaciar")
    public Carrito vaciar(@PathVariable Long usuarioId) {
        return carritoService.vaciarCarrito(usuarioId);
    }
}
