package com.bancolombia.services;

import com.bancolombia.domain.entities.Cliente;
import com.bancolombia.domain.repositories.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    ClienteRepository repository;


    @InjectMocks
    ClienteService clienteService;


    @Test
    void crear() {
        var cliente = new Cliente();
        cliente.setEmail("raul@email.com");
        cliente.setNombre("Raul");


        Mockito.when(repository.save(cliente)).thenReturn(Mono.just(cliente));

        StepVerifier.create(clienteService.crear(cliente))
                .expectNext(cliente)
                .verifyComplete();
    }

    @Test
    void obtener() {
        var cliente = new Cliente();
        cliente.setEmail("raul@email.com");
        cliente.setNombre("Raul");
        cliente.setId(1L);


        Mockito.when(repository.findById(1L)).thenReturn(Mono.just(cliente));

        StepVerifier.create(clienteService.obtenerPorId(1L))
                .expectNext(cliente)
                .verifyComplete();

    }

    @Test
    void obtener_NoFound() {
        Mockito.when(repository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(clienteService.obtenerPorId(1L))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void obtenerPorId() {
        var cliente = new Cliente();

        Mockito.when(repository.findById(1L)).thenReturn(Mono.just(cliente));

        StepVerifier.create(clienteService.obtenerPorId(1L))
                .expectNext(cliente)
                .verifyComplete();

    }

    @Test
    void obtenerTodos() {
        var cliente1 = new Cliente();
        var cliente2 = new Cliente();

        Mockito.when(repository.findAll()).thenReturn(Flux.just(cliente1, cliente2));

        StepVerifier.create(clienteService.obtener())
                .expectNext(cliente1)
                .expectNext(cliente2)
                .verifyComplete();

    }
}