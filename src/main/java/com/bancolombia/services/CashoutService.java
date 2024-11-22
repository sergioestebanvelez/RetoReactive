package com.bancolombia.services;

import com.bancolombia.domain.entities.Cashout;
import com.bancolombia.domain.repositories.CashoutRepository;
import com.bancolombia.exceptions.InsufficientBalanceException;
import com.bancolombia.exceptions.PaymentRejectedException;
import com.bancolombia.microservices.PaymentMicroservice;
import com.bancolombia.microservices.PaymentMicroserviceMock;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CashoutService {
    private final PaymentMicroserviceMock paymentMicroservice;
    private final CashoutRepository repository;

    public CashoutService(PaymentMicroserviceMock paymentMicroservice, CashoutRepository repository) {
        this.paymentMicroservice = paymentMicroservice;
        this.repository = repository;
    }

    public Mono<Cashout> crear(Long userId, Double amount) {
        return paymentMicroservice.processPayment(userId, amount)
                .flatMap(status -> {
                    if ("approved".equals(status)) {
                        return repository.save(new Cashout(userId, amount));
                    } else {
                        return Mono.error(new RuntimeException("Payment failed: " + status));
                    }
                });
    }

    public Flux<Cashout> obtenerHistorial(Long userId) {
        return repository.findByUserId(userId);
    }
}