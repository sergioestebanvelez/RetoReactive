package com.bancolombia.controllers;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/validaciones")
public class ValidacionesController {

    @GetMapping("/cliente/{clienteId}")
    public Mono<String> validacion(@PathVariable("clienteId") Long clienteId){
       if(clienteId % 3 == 0){
           return Mono.just("Cliente no es apto para la poliza");
       } else if (clienteId % 3 == 1) {
           return Mono.just("Cliente debe tener un proceso de 3 dias para evaluar la poliza");
       }
       return Mono.just("Cliente es apto para la poliza");
    }
}
