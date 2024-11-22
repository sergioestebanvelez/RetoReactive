package com.bancolombia.microservices;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentMicroservice {

    private final WebClient webClient;

    public PaymentMicroservice(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://payment-microservice-url.com").build();
    }

    /**
     * Procesar un pago con el microservicio de pagos.
     * @param userId ID del usuario.
     * @param amount Monto del cashout.
     * @return Mono con el estado del pago (approved, rejected, etc.).
     */
    public Mono<String> processPayment(Long userId, Double amount) {
        return webClient.post()
                .uri("/payments")
                .bodyValue(new PaymentRequest(userId, amount))
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .map(PaymentResponse::getStatus);
    }

    /**
     * Clase interna para la solicitud de pago.
     */
    private static class PaymentRequest {
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

    /**
     * Clase interna para la respuesta del pago.
     */
    private static class PaymentResponse {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
