package com.bancolombia;

import com.bancolombia.controllers.ClienteController;
import com.bancolombia.controllers.PolizaController;
import com.bancolombia.domain.entities.Cliente;
import com.bancolombia.domain.entities.Poliza;
import com.bancolombia.domain.repositories.ClienteRepository;
import com.bancolombia.domain.repositories.PolizaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(PolizaController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestPolizaControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;
    @Autowired ClienteRepository repository;


    @AfterAll
    static void setup(@Autowired PolizaRepository repository){
        repository.deleteAll().block();
    }


    @Test
    @Order(1)
    void crearPoliza(){
        var cliente = new Cliente();
        cliente.setNombre("Andres");
        cliente.setEmail("andres32323@email.com");
        var newCliente = repository.save(cliente).block();

        var poliza = new Poliza();
        poliza.setClienteId(newCliente.getId());
        poliza.setDescripcion("Poliza de seguro");
        poliza.setTipo("Seguro");

        var newPolizas = webTestClient.post()
                .uri("/polizas")
                .bodyValue(poliza)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Poliza.class)
                .returnResult()
                .getResponseBody();

        System.out.println(newPolizas);
    }


}
