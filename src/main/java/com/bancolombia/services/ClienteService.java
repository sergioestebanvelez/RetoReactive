package com.bancolombia.services;

import com.bancolombia.domain.entities.Cliente;
import com.bancolombia.domain.repositories.ClienteRepository;
import com.bancolombia.exceptions.ClienteNoEncotradoException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository){

        this.repository = repository;
    }

    public Mono<Cliente> crear(Cliente cliente){
        return repository.save(cliente);
    }

    public Flux<Cliente> obtener(){
        return repository.findAll();
    }

    public Mono<Cliente> obtenerPorId(Long id){
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ClienteNoEncotradoException("Cliente no encotrado")));
    }
 }
