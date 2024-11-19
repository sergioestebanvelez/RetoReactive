package com.bancolombia.services;

import com.bancolombia.domain.entities.Cliente;
import com.bancolombia.domain.entities.Poliza;
import com.bancolombia.domain.repositories.PolizaRepository;
import com.bancolombia.exceptions.Error400Exception;
import com.bancolombia.exceptions.NoValidoClienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PolizaServiceTest {

    @Mock
    PolizaRepository repository;
    @Mock
    ClienteService clienteService;
    @Mock
    RestPolizaValidacion restPolizaValidacion;

    @InjectMocks
    PolizaService polizaService;


    @Test
    void crearPoliza() {
        var poliza = new Poliza();
        poliza.setClienteId(3L);

        var cliente = new Cliente();
        cliente.setNombre("Raul");
        cliente.setId(3L);

        Mockito.when(clienteService.obtenerPorId(3L)).thenReturn(Mono.just(cliente));
        Mockito.when(restPolizaValidacion.validarCliente(3L)).thenReturn(Mono.just("Cliente es apto para la poliza"));
        Mockito.when(repository.save(poliza)).thenReturn(Mono.just(poliza));

        StepVerifier.create(polizaService.crearPoliza(poliza))
                .expectNext(poliza)
                .verifyComplete();

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Cliente no es apto para la poliza",
            "Cliente debe tener un proceso de 3 dias para evaluar la poliza",
            "-- Caso no exite --"
    })
    void crearPoliza_Error400(String messageError) {
        var poliza = new Poliza();
        poliza.setClienteId(3L);

        var cliente = new Cliente();
        cliente.setNombre("Raul");
        cliente.setId(3L);

        Mockito.when(clienteService.obtenerPorId(3L)).thenReturn(Mono.just(cliente));
        Mockito.when(restPolizaValidacion.validarCliente(3L)).thenReturn(Mono.just(messageError));

        StepVerifier.create(polizaService.crearPoliza(poliza))
                .expectError(Error400Exception.class)
                .verify();

    }

    @Test
    void crearPoliza_clienteNoFound() {
        var poliza = new Poliza();
        poliza.setClienteId(3L);


        Mockito.when(clienteService.obtenerPorId(3L)).thenReturn(Mono.error(new IllegalArgumentException("Cliente no encotrado")));

        StepVerifier.create(polizaService.crearPoliza(poliza))
                .expectError(NoValidoClienteException.class)
                .verify();

    }

    @Test
    void obtenerPolizaPorClienteId() {
        var poliza1 = new Poliza();
        var poliza2 = new Poliza();

        Mockito.when(repository.findByClienteId(1L)).thenReturn(Flux.just(poliza1, poliza2));

        StepVerifier.create(polizaService.obtenerPolizaPorClienteId(1L))
                .expectNext(poliza1)
                .expectNext(poliza2)
                .verifyComplete();
    }

    @Test
    void obtenerPolizaPorClienteId_noFound() {


        Mockito.when(repository.findByClienteId(1L)).thenReturn(Flux.empty());

        StepVerifier.create(polizaService.obtenerPolizaPorClienteId(1L))
                .expectErrorMessage("No se encuetra la poliza del cliente")
                .verify();
    }
}