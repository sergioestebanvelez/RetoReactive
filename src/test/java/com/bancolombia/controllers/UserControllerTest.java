package com.bancolombia.controllers;

import com.bancolombia.domain.entities.User;
import com.bancolombia.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private WebTestClient webClient;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webClient = WebTestClient.bindToController(userController).build();
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setBalance(100.0);
    }

    @Test
    public void testCrearUsuario() {
        // Mock de la respuesta del servicio
        when(userService.crear(any(User.class))).thenReturn(Mono.just(user));

        // Realizar la petición POST al endpoint
        webClient.post()
                .uri("/users")
                .body(BodyInserters.fromValue(user))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1L)
                .jsonPath("$.name").isEqualTo("John Doe")
                .jsonPath("$.balance").isEqualTo(100.0);

        // Verificar que se haya llamado al servicio
        verify(userService, times(1)).crear(any(User.class));
    }

    @Test
    public void testObtenerUsuarioPorId() {
        // Mock de la respuesta del servicio
        when(userService.obtenerPorId(anyLong())).thenReturn(Mono.just(user));

        // Realizar la petición GET al endpoint
        webClient.get()
                .uri("/users/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1L)
                .jsonPath("$.name").isEqualTo("John Doe")
                .jsonPath("$.balance").isEqualTo(100.0);

        // Verificar que se haya llamado al servicio
        verify(userService, times(1)).obtenerPorId(anyLong());
    }

    @Test
    public void testActualizarSaldo() {
        // Nuevo saldo
        Double newBalance = 150.0;
        user.setBalance(newBalance);

        // Mock de la respuesta del servicio
        when(userService.actualizarSaldo(anyLong(), anyDouble())).thenReturn(Mono.just(user));

        // Realizar la petición PUT al endpoint
        webClient.put()
                .uri("/users/{id}/balance", 1L)
                .bodyValue(newBalance)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1L)
                .jsonPath("$.balance").isEqualTo(newBalance);

        // Verificar que se haya llamado al servicio
        verify(userService, times(1)).actualizarSaldo(anyLong(), anyDouble());
    }
}
