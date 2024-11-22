package com.bancolombia.controllers;

import com.bancolombia.domain.entities.Cashout;
import com.bancolombia.services.CashoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CashoutControllerTest {

    private WebTestClient webClient;

    @Mock
    private CashoutService cashoutService;

    @InjectMocks
    private CashoutController cashoutController;

    private Cashout cashout;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webClient = WebTestClient.bindToController(cashoutController).build();
        // Crear un Cashout usando el constructor con parámetros
        cashout = new Cashout(1L, 50.0);  // Asumiendo userId = 1L y amount = 50.0
    }

    @Test
    public void testCrearCashout() {
        // Mock de la respuesta del servicio
        when(cashoutService.crear(anyLong(), anyDouble())).thenReturn(Mono.just(cashout));

        // Realizar la petición POST al endpoint
        webClient.post()
                .uri("/cashouts")
                .body(BodyInserters.fromValue(cashout))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1L)
                .jsonPath("$.amount").isEqualTo(50.0)
                .jsonPath("$.userId").isEqualTo(1L);

        // Verificar que se haya llamado al servicio
        verify(cashoutService, times(1)).crear(anyLong(), anyDouble());
    }
}
