package com.bancolombia.controllers;

import com.bancolombia.domain.entities.Poliza;
import com.bancolombia.services.PolizaService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/polizas")
public class PolizaController {

    private final PolizaService service;

    public PolizaController(PolizaService service){

        this.service = service;
    }

    @PostMapping
    public Mono<Poliza> crearPoliza(@Valid @RequestBody Poliza poliza){
        return service.crearPoliza(poliza);
    }

    @GetMapping("/{clienteId}/cliente")
    public Flux<Poliza> obtenerPoliza(@PathVariable("clienteId") Long clienteId){
        return service.obtenerPolizaPorClienteId(clienteId);
    }

}
