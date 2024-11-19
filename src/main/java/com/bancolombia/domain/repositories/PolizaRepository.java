package com.bancolombia.domain.repositories;

import com.bancolombia.domain.entities.Poliza;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PolizaRepository extends ReactiveCrudRepository<Poliza, Long> {
    Flux<Poliza> findByClienteId(Long clienteId);
}
