package com.bancolombia.services;

import com.bancolombia.domain.entities.User;
import com.bancolombia.domain.repositories.UserRepository;
import com.bancolombia.exceptions.UserNoEncontradoException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class UserServiceTest {

    @Test
    void obtenerPorId_UsuarioEncontrado() {
        var repository = Mockito.mock(UserRepository.class);
        var userService = new UserService(repository);

        var user = new User();
        user.setId(1L);
        user.setBalance(100.0);

        Mockito.when(repository.findById(1L)).thenReturn(Mono.just(user));

        StepVerifier.create(userService.obtenerPorId(1L))
                .expectNextMatches(u -> u.getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void obtenerPorId_UsuarioNoEncontrado() {
        var repository = Mockito.mock(UserRepository.class);
        var userService = new UserService(repository);

        Mockito.when(repository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(userService.obtenerPorId(1L))
                .expectErrorMatches(e -> e instanceof UserNoEncontradoException)
                .verify();
    }
}
