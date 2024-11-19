package com.bancolombia.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {ValidacionesController.class, GlobalHandleException.class})
@WebFluxTest(ValidacionesController.class)
class ValidacionesControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @ParameterizedTest
    @CsvSource({
            "1,Cliente debe tener un proceso de 3 dias para evaluar la poliza",
            "2,Cliente es apto para la poliza",
            "3,Cliente no es apto para la poliza"
    })
    void validacion(long id, String message) {
        var result = webTestClient
                .get()
                .uri("/validaciones/cliente/{clienteId}", id)
                .exchange()
                .expectBody(String.class)
                .returnResult();

        Assertions.assertEquals(result.getResponseBody(), message);
    }
}