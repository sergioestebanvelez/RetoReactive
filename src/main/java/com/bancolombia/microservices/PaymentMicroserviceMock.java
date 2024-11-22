package com.bancolombia.microservices;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentMicroserviceMock {

    /**
     * Simula el procesamiento de un pago con diferentes escenarios según el monto.
     *
     * @param userId ID del usuario.
     * @param amount Monto del cashout.
     * @return Mono con el estado del pago (approved, rejected, etc.).
     */
    public Mono<String> processPayment(Long userId, Double amount) {
        // Lógica simulada para diferentes resultados basados en el monto
        if (amount > 1000) {
            return Mono.just("rejected"); // Rechazado para montos mayores a 1000
        } else if (amount <= 0) {
            return Mono.error(new RuntimeException("Invalid amount")); // Error para montos inválidos
        } else {
            return Mono.just("approved"); // Aprobado para el resto de casos
        }
    }
}
