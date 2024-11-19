package com.bancolombia.controllers;

import com.bancolombia.domain.entities.Cashout;
import com.bancolombia.domain.entities.User;
import com.bancolombia.domain.repositories.CashoutRepository;
import com.bancolombia.domain.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestCashoutControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    private static final AtomicLong userId = new AtomicLong();
    private static final AtomicLong cashoutId = new AtomicLong();

    @AfterAll
    static void cleanup(@Autowired UserRepository userRepository,
                        @Autowired CashoutRepository cashoutRepository) {
        userRepository.deleteAll().block();
        cashoutRepository.deleteAll().block();
    }

    @Test
    @Order(1)
    void createUser() {
        var user = new User();
        user.setName("Alice");
        user.setBalance(100.0);

        var newUser = webTestClient.post()
                .uri("/users")
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();

        userId.set(Objects.requireNonNull(newUser).getId());
    }

    @Test
    @Order(2)
    void createCashout() {
        var cashoutRequest = new Cashout();
        cashoutRequest.setUserId(userId.get());
        cashoutRequest.setAmount(50.0);

        var newCashout = webTestClient.post()
                .uri("/cashouts")
                .bodyValue(cashoutRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Cashout.class)
                .returnResult()
                .getResponseBody();

        cashoutId.set(Objects.requireNonNull(newCashout).getId());
    }

    @Test
    @Order(3)
    void getUserCashouts() {
        webTestClient.get()
                .uri("/cashouts/user/{userId}", userId.get())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].userId").isEqualTo(userId.get())
                .jsonPath("$[0].amount").isEqualTo(50.0);
    }
}
