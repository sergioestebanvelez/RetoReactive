package com.bancolombia.controllers;

import com.bancolombia.domain.entities.Poliza;
import com.bancolombia.services.PolizaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.refEq;

@ContextConfiguration(classes = {PolizaController.class, GlobalHandleException.class})
@WebFluxTest(PolizaController.class)
class PolizaControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    PolizaService service;

    @Test
    void crearPoliza() {
        var poliza = new Poliza();
        poliza.setClienteId(3L);
        poliza.setDescripcion("Poliza de seguro");
        poliza.setTipo("Seguro");

        Mockito.when(service.crearPoliza(refEq(poliza))).thenReturn(Mono.just(poliza));

        var resultPoliza = webTestClient.post()
                .uri("/polizas")
                .bodyValue(poliza)
                .exchange()
                .expectBody(Poliza.class)
                .returnResult()
                .getResponseBody();

        assert resultPoliza != null;
        Assertions.assertEquals(resultPoliza.getClienteId(), 3L);
        Assertions.assertEquals(resultPoliza.getDescripcion(), "Poliza de seguro");
        Assertions.assertEquals(resultPoliza.getTipo(), "Seguro");
    }

    @Test
    void crearPoliza_sadPath() {
        var poliza = new Poliza();
        poliza.setClienteId(3L);
        poliza.setDescripcion("Poliza de seguro");

        Mockito.when(service.crearPoliza(refEq(poliza))).thenReturn(Mono.just(poliza));

        var resultPoliza = webTestClient.post()
                .uri("/polizas")
                .bodyValue(poliza)
                .exchange()
                .expectBody(Poliza.class)
                .returnResult()
                .getResponseBody();

        assert resultPoliza != null;
        Assertions.assertEquals(resultPoliza.getClienteId(), 3L);
        Assertions.assertEquals(resultPoliza.getDescripcion(), "Poliza de seguro");
        Assertions.assertEquals(resultPoliza.getTipo(), "Seguro");
    }

    @Test
    void obtenerPolizas() {
        var poliza = new Poliza();
        poliza.setClienteId(3L);
        poliza.setDescripcion("Poliza de seguro");
        poliza.setTipo("Seguro");
        Mockito.when(service.obtenerPolizaPorClienteId(anyLong()))
                .thenReturn(Flux.just(poliza));

        var resultPoliza = webTestClient.get()
                .uri("/polizas/{clienteId}/cliente", 3L)
                .exchange()
                .expectBodyList(Poliza.class)
                .returnResult()
                .getResponseBody();

        assert resultPoliza != null;
        Assertions.assertEquals(resultPoliza.get(0).getClienteId(), 3L);
        Assertions.assertEquals(resultPoliza.get(0).getDescripcion(), "Poliza de seguro");
        Assertions.assertEquals(resultPoliza.get(0).getTipo(), "Seguro");

    }
}