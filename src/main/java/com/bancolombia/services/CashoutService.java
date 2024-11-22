package com.bancolombia.services;

import com.bancolombia.domain.entities.Cashout;
import com.bancolombia.domain.repositories.CashoutRepository;
import com.bancolombia.exceptions.InsufficientBalanceException;
import com.bancolombia.exceptions.PaymentRejectedException;
import com.bancolombia.microservices.PaymentMicroservice;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CashoutService {

    private final CashoutRepository repository;
    private final PaymentMicroservice paymentMicroservice;
    private final UserService userService;

    public CashoutService(CashoutRepository repository, PaymentMicroservice paymentMicroservice, UserService userService) {
        this.repository = repository;
        this.paymentMicroservice = paymentMicroservice;
        this.userService = userService;
    }

    public Mono<Cashout> crear(Long userId, Double amount) {
        return userService.obtenerPorId(userId)
                .flatMap(user -> {
                    if (user.getBalance() < amount) {
                        return Mono.error(new InsufficientBalanceException("Insufficient balance for cashout."));
                    }
                    return paymentMicroservice.processPayment(userId, amount)
                            .flatMap(paymentStatus -> {
                                if (!"approved".equalsIgnoreCase(paymentStatus)) {
                                    return Mono.error(new PaymentRejectedException("Payment was rejected by the payment service."));
                                }
                                user.setBalance(user.getBalance() - amount);
                                return userService.actualizarSaldo(userId, -amount)
                                        .then(repository.save(new Cashout(userId, amount)));
                            });
                });
    }



}
