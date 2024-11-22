package com.bancolombia.services;

import com.bancolombia.domain.entities.User;
import com.bancolombia.domain.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class UserServiceTest {
    private final UserRepository repository = Mockito.mock(UserRepository.class);
    private final UserService service = new UserService(repository);

    @Test
    void testObtenerPorId() {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setBalance(100.0);

        Mockito.when(repository.findById(1L)).thenReturn(Mono.just(user));

        Mono<User> result = service.obtenerPorId(1L);

        StepVerifier.create(result)
                .expectNextMatches(u -> u.getName().equals("John") && u.getBalance().equals(100.0))
                .verifyComplete();
    }
}
