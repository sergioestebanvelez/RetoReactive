package com.bancolombia.controllers;

import com.bancolombia.domain.entities.User;
import com.bancolombia.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Mono<User> obtenerUsuario(@PathVariable Long id) {
        return userService.obtenerPorId(id);
    }

    @PutMapping("/{id}/balance")
    public Mono<User> actualizarSaldo(@PathVariable Long id, @RequestBody Double cambioSaldo) {
        return userService.actualizarSaldo(id, cambioSaldo);
    }

    @PostMapping
    public Mono<User> crearUsuario(@Valid @RequestBody User user) {
        return userService.crear(user);
    }
}
