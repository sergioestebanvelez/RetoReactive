package com.bancolombia.domain.repositories;

import com.bancolombia.domain.entities.Cashout;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CashoutRepository extends ReactiveCrudRepository<Cashout, Long> {
    // Métodos adicionales si es necesario
    Flux<Cashout> findByUserId(Long userId);
}
