package com.bancolombia.services;

import com.bancolombia.domain.entities.Cliente;
import com.bancolombia.domain.entities.Poliza;
import com.bancolombia.domain.repositories.PolizaRepository;
import com.bancolombia.exceptions.Error400Exception;
import com.bancolombia.exceptions.NoValidoClienteException;
import com.bancolombia.exceptions.PolizaNoEncontradaException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Service
public class PolizaService {

    private final PolizaRepository repository;
    private final ClienteService clienteService;
    private final RestPolizaValidacion restPolizaValidacion;

    public PolizaService(PolizaRepository repository, ClienteService clienteService, RestPolizaValidacion restPolizaValidacion){

        this.repository = repository;
        this.clienteService = clienteService;
        this.restPolizaValidacion = restPolizaValidacion;
    }

    public Mono<Poliza> crearPoliza(Poliza poliza){
        return clienteService.obtenerPorId(poliza.getClienteId())
                .onErrorMap(error -> new NoValidoClienteException(error.getMessage()))
                .doOnError(error -> {
                    System.out.println(error.getMessage());
                })
                .flatMap(cliente -> validarCliente(poliza, cliente));
    }

    public Flux<Poliza> obtenerPolizaPorClienteId(Long clienteId){
        return repository.findByClienteId(clienteId)
                .switchIfEmpty(Mono.error(new PolizaNoEncontradaException("No se encuetra la poliza del cliente")));
    }

    private Mono<Poliza> validarCliente(Poliza poliza, Cliente cliente) {
        return restPolizaValidacion.validarCliente(cliente.getId())
                .flatMap(resultado -> switch (resultado) {
                    case "Cliente no es apto para la poliza",
                         "Cliente debe tener un proceso de 3 dias para evaluar la poliza" ->
                            Mono.error(new Error400Exception(resultado));
                    case "Cliente es apto para la poliza" -> repository.save(poliza);
                    default -> Mono.error(new Error400Exception("Resultado no valido"));
                });
    }
}
