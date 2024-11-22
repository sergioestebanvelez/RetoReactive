package com.bancolombia.controllers;

import com.bancolombia.domain.entities.Cashout;
import com.bancolombia.services.CashoutService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cashouts")
public class CashoutController {
    private final CashoutService service;

    public CashoutController(CashoutService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<Cashout> crearCashout(@RequestBody Cashout cashout) {
        return service.crear(cashout.getUserId(), cashout.getAmount());
    }


}
