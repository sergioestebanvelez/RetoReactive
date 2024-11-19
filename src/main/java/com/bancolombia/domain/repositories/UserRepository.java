package com.bancolombia.domain.repositories;

import com.bancolombia.domain.entities.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    /**
     * Encontrar un usuario por su ID.
     * @param id ID del usuario.
     * @return Mono del usuario encontrado.
     */
    Mono<User> findById(Long id);
}
