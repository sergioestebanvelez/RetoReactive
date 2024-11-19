package com.bancolombia.services;

import com.bancolombia.domain.entities.User;
import com.bancolombia.domain.repositories.UserRepository;
import com.bancolombia.exceptions.UserNoEncontradoException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<User> obtenerPorId(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new UserNoEncontradoException("Usuario no encontrado")));
    }

    public Mono<User> actualizarSaldo(Long userId, Double cambioSaldo) {
        return repository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNoEncontradoException("Usuario no encontrado")))
                .flatMap(user -> {
                    user.setBalance(user.getBalance() + cambioSaldo);
                    return repository.save(user);
                });
    }
}
