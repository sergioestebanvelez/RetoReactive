package com.bancolombia.domain.repositories;

import com.bancolombia.domain.entities.Cashout;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CashoutRepository extends ReactiveCrudRepository<Cashout, Long> {

    /**
     * Buscar cashouts por el ID de un usuario.
     * @param userId ID del usuario.
     * @return Flux de cashouts asociados al usuario.
     */
    Flux<Cashout> findByUserId(Long userId);
}
