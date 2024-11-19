package com.bancolombia;

import com.bancolombia.controllers.ClienteController;
import com.bancolombia.domain.entities.Cliente;
import com.bancolombia.domain.repositories.ClienteRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ClienteController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestClienteControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    private static final AtomicLong clienteId = new AtomicLong();

    @AfterAll
    static void setup(@Autowired ClienteRepository repository){
        repository.deleteAll().block();
    }

    @Test
    @Order(1)
    void crearCliente(){
        var cliente = new Cliente();
        cliente.setNombre("Andres");
        cliente.setEmail("andres@email.com");

        var newCliente = webTestClient.post()
                .uri("/clientes")
                .bodyValue(cliente)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cliente.class)
                .returnResult()
                .getResponseBody();

        clienteId.set(Objects.requireNonNull(newCliente).getId());
    }

    @Test
    @Order(2)
    void consultaTodo(){
        webTestClient.get()
                .uri("/clientes")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].nombre").isEqualTo("Andres")
                .jsonPath("$[0].email").isEqualTo("andres@email.com");
    }

    @Test
    @Order(3)
    void consultaPorId(){
        webTestClient.get()
                .uri("/clientes/{id}", clienteId.get())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("Andres")
                .jsonPath("$.email").isEqualTo("andres@email.com");
    }

}
