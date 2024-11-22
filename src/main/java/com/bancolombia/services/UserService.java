package com.bancolombia.services;

import com.bancolombia.domain.entities.User;
import com.bancolombia.domain.repositories.UserRepository;
import com.bancolombia.exceptions.*;
import jakarta.validation.Valid;
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
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + id)));
    }

    public Mono<User> actualizarSaldo(Long id, Double cambioSaldo) {
        return obtenerPorId(id).flatMap(user -> {
            if (user.getBalance() + cambioSaldo < 0) {
                return Mono.error(new InsufficientBalanceException("Insufficient balance to complete the operation."));
            }
            user.setBalance(user.getBalance() + cambioSaldo);
            return repository.save(user);
        });
    }


    public Mono<User> crear(User user) {
        return repository.save(user); // Guarda el usuario en el repositorio.
    }
}
