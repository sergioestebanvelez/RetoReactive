package com.bancolombia.controllers;

import com.bancolombia.domain.entities.Cashout;
import com.bancolombia.services.CashoutService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/cashouts")
public class CashoutController {

    private final CashoutService service;

    public CashoutController(CashoutService service) {
        this.service = service;
    }

    /**
     * Crear un nuevo cashout.
     * @param cashout Objeto Cashout con los datos a crear.
     * @return El cashout creado.
     */
    @PostMapping
    public Mono<Cashout> crearCashout(@Valid @RequestBody Cashout cashout) {
        return service.crear(cashout);
    }

    /**
     * Obtener un cashout por su ID.
     * @param id ID del cashout.
     * @return El cashout encontrado.
     */
    @GetMapping("/{id}")
    public Mono<Cashout> obtenerPorId(@PathVariable("id") Long id) {
        return service.obtenerPorId(id);
    }

    /**
     * Obtener todos los cashouts.
     * @return Lista de todos los cashouts.
     */
    @GetMapping
    public Flux<Cashout> obtenerTodos() {
        return service.obtener();
    }

    /**
     * Obtener los cashouts de un usuario específico.
     * @param userId ID del usuario.
     * @return Lista de cashouts asociados al usuario.
     */
    @GetMapping("/user/{userId}")
    public Flux<Cashout> obtenerPorUsuario(@PathVariable("userId") Long userId) {
        return service.obtenerPorUsuario(userId);
    }
}
