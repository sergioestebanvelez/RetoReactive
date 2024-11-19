package com.bancolombia.controllers;

import com.bancolombia.domain.entities.Cliente;
import com.bancolombia.services.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.refEq;


@ContextConfiguration(classes = {ClienteController.class, GlobalHandleException.class})
@WebFluxTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ClienteService service;

    @Test
    void crearCliente() {
        var cliente = new Cliente();
        cliente.setNombre("Raul");
        cliente.setEmail("raul@email.com");

        Mockito.when(service.crear(refEq(cliente))).thenReturn(Mono.just(cliente));

        webTestClient.post()
                .uri("/clientes")
                .bodyValue(cliente)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("Raul")
                .jsonPath("$.email").isEqualTo("raul@email.com");

    }

    @ParameterizedTest
    @CsvSource({
            "raul,,Email requerido",
            ",raulalzate@gmail.com,Nombre requerido"

    })
    void crearCliente_sandPath(String nombre, String email, String errorMensaje) {
        var cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);

        Mockito.when(service.crear(refEq(cliente))).thenReturn(Mono.just(cliente));

        webTestClient.post()
                .uri("/clientes")
                .bodyValue(cliente)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class).isEqualTo(errorMensaje);

    }

    @Test
    void obtenerPorId() {
        var cliente = new Cliente();
        cliente.setNombre("Raul");
        cliente.setEmail("raul@email.com");

        Mockito.when(service.obtenerPorId(1L)).thenReturn(Mono.just(cliente));


        webTestClient.get()
                .uri("/clientes/{id}", 1L)
                .exchange()
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("Raul")
                .jsonPath("$.email").isEqualTo("raul@email.com");

    }

    @Test
    void obtenerTodos() {

        var cliente1 = new Cliente();
        cliente1.setNombre("raul");
        var cliente2 = new Cliente();
        cliente2.setNombre("andres");

        Mockito.when(service.obtener()).thenReturn(Flux.just(cliente1, cliente2));


        webTestClient.get()
                .uri("/clientes")
                .exchange()
                .expectBody()
                .jsonPath("$[0].nombre").isEqualTo("raul")
                .jsonPath("$[1].nombre").isEqualTo("andres");
    }
}