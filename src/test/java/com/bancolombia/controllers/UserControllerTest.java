package com.bancolombia.controllers;

import com.bancolombia.domain.entities.User;
import com.bancolombia.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {UserController.class, GlobalHandleException.class})
@WebFluxTest(UserController.class)
class UserControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    UserService userService;

    @Test
    void crearUsuario() {
        var user = new User();
        user.setName("Carlos");
        user.setEmail("carlos@email.com");

        Mockito.when(userService.create(Mockito.any(User.class))).thenReturn(Mono.just(user));

        webTestClient.post()
                .uri("/users")
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Carlos")
                .jsonPath("$.email").isEqualTo("carlos@email.com");
    }

    @ParameterizedTest
    @CsvSource({
            "Carlos,,Email requerido",
            ",carlos@email.com,Nombre requerido"
    })
    void crearUsuarioConDatosInvalidos(String name, String email, String errorMessage) {
        var user = new User();
        user.setName(name);
        user.setEmail(email);

        Mockito.when(userService.create(Mockito.any(User.class))).thenReturn(Mono.just(user));

        webTestClient.post()
                .uri("/users")
                .bodyValue(user)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class).isEqualTo(errorMessage);
    }

    @Test
    void obtenerUsuarioPorId() {
        var user = new User();
        user.setName("Carlos");
        user.setEmail("carlos@email.com");

        Mockito.when(userService.getUserById(1L)).thenReturn(Mono.just(user));

        webTestClient.get()
                .uri("/users/{id}", 1L)
                .exchange()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Carlos")
                .jsonPath("$.email").isEqualTo("carlos@email.com");
    }
}
