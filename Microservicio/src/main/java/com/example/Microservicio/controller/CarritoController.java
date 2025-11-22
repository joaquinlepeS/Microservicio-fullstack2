package com.example.Microservicio.controller;

import com.example.Microservicio.model.CarritoItem;
import com.example.Microservicio.model.Usuario;
import com.example.Microservicio.service.CarritoService;
import com.example.Microservicio.service.UsuarioService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cliente/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    private final CarritoService carritoService;
    private final UsuarioService usuarioService;

    public CarritoController(CarritoService carritoService,
                             UsuarioService usuarioService) {
        this.carritoService = carritoService;
        this.usuarioService = usuarioService;
    }

    private Usuario obtenerUsuario(Authentication auth) {
        String email = auth.getName();
        return usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // 🔵 Listar carrito del usuario logueado
    @GetMapping
    public List<CarritoItem> listar(Authentication auth) {
        Usuario usuario = obtenerUsuario(auth);
        return carritoService.listarPorUsuario(usuario);
    }

    // 🔴 Agregar producto al carrito
    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody CarritoRequest request,
                                     Authentication auth) {
        Usuario usuario = obtenerUsuario(auth);
        CarritoItem item = carritoService.agregarProducto(
                usuario,
                request.getProductoId(),
                request.getCantidad()
        );
        return ResponseEntity.ok(item);
    }

    // 🟡 Actualizar cantidad de un item
    @PutMapping("/{itemId}")
    public ResponseEntity<?> actualizar(@PathVariable Long itemId,
                                        @RequestBody CarritoUpdateRequest request,
                                        Authentication auth) {
        Usuario usuario = obtenerUsuario(auth);
        CarritoItem item = carritoService.actualizarCantidad(usuario, itemId, request.getCantidad());
        return ResponseEntity.ok(item);
    }

    // 🔴 Eliminar un item del carrito
    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> eliminar(@PathVariable Long itemId,
                                      Authentication auth) {
        Usuario usuario = obtenerUsuario(auth);
        carritoService.eliminarItem(usuario, itemId);
        return ResponseEntity.noContent().build();
    }

    // 🔴 Vaciar carrito
    @DeleteMapping("/vaciar")
    public ResponseEntity<?> vaciar(Authentication auth) {
        Usuario usuario = obtenerUsuario(auth);
        carritoService.vaciarCarrito(usuario);
        return ResponseEntity.noContent().build();
    }

    // DTOs simples para requests
    @Data
    public static class CarritoRequest {
        private Long productoId;
        private Integer cantidad;
    }

    @Data
    public static class CarritoUpdateRequest {
        private Integer cantidad;
    }
}
