package com.bancolombia.controllers;

import com.bancolombia.microservices.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mock-payment-service")
public class MockPaymentMicroservice {

    @PostMapping("/payments")
    @ResponseStatus(HttpStatus.OK)
    public Mono<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        // Simula una respuesta de pago "aprobado"
        PaymentResponse response = new PaymentResponse();
        response.setStatus("approved");

        return Mono.just(response);
    }

    public static class PaymentRequest {
        private Long userId;
        private Double amount;

        public PaymentRequest(Long userId, Double amount) {
            this.userId = userId;
            this.amount = amount;
        }

        public Long getUserId() {
            return userId;
        }

        public Double getAmount() {
            return amount;
        }
    }

    public static class PaymentResponse {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
