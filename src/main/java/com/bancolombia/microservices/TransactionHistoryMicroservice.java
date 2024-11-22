package com.bancolombia.microservices;

import com.bancolombia.domain.entities.Cashout;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionHistoryMicroservice {

    /**
     * Simula la obtención de una lista de transacciones de cashout para un usuario.
     * @param userId ID del usuario.
     * @return Lista de cashouts asociados al usuario.
     */
    public Flux<Cashout> obtenerHistorialTransacciones(Long userId) {
        // Simulación de datos.
        List<Cashout> cashouts = new ArrayList<>();

        // Crear instancias de Cashout usando el constructor con parámetros
        cashouts.add(new Cashout(userId, 50.0)); // Pasamos el userId y el amount
        cashouts.add(new Cashout(userId, 30.0)); // Lo mismo para el siguiente cashout

        // Devolver los cashouts como un Flux
        return Flux.fromIterable(cashouts);
    }
}
