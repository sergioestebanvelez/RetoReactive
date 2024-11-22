package com.bancolombia.controllers;

import com.bancolombia.domain.entities.Cashout;
import com.bancolombia.microservices.PaymentMicroserviceMock;
import com.bancolombia.domain.repositories.CashoutRepository;
import com.bancolombia.services.CashoutService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class CashoutServiceTest {

    @Test
    void crearCashoutExito() {
        var mockMicroservice = new PaymentMicroserviceMock();
        var mockRepository = Mockito.mock(CashoutRepository.class);

        Mockito.when(mockRepository.save(Mockito.any()))
                .thenReturn(Mono.just(new Cashout(1L, 50.0)));

        var service = new CashoutService(mockMicroservice, mockRepository);

        StepVerifier.create(service.crear(1L, 50.0))
                .expectNextMatches(cashout -> cashout.getUserId().equals(1L) && cashout.getAmount().equals(50.0))
                .verifyComplete();
    }

    @Test
    void crearCashoutMontoRechazado() {
        var mockMicroservice = new PaymentMicroserviceMock();
        var mockRepository = Mockito.mock(CashoutRepository.class);

        var service = new CashoutService(mockMicroservice, mockRepository);

        StepVerifier.create(service.crear(1L, 1500.0))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Payment failed: rejected"))
                .verify();
    }
}
