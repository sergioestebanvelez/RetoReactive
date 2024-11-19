package com.bancolombia.controllers;

import com.bancolombia.domain.entities.Cliente;
import com.bancolombia.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service){

        this.service = service;
    }

    @PostMapping
    public Mono<Cliente> crearCliente(@Valid @RequestBody Cliente cliente){
        return service.crear(cliente);
    }

    @GetMapping("/{id}")
    public Mono<Cliente> obtenerPorId(@PathVariable("id") Long id){
        return service.obtenerPorId(id);
    }

    @GetMapping
    public Flux<Cliente> obtenerTodos(){
        return service.obtener();
    }
}
