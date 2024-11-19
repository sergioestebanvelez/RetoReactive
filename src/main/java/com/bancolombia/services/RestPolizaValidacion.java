package com.bancolombia.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

public interface RestPolizaValidacion {
    @GetExchange("/validaciones/cliente/{clienteId}")
    Mono<String> validarCliente(@PathVariable("clienteId") Long clienteId);
}
