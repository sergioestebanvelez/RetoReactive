package com.bancolombia.services;

import com.bancolombia.domain.entities.Cashout;
import com.bancolombia.domain.repositories.CashoutRepository;
import com.bancolombia.exceptions.CashoutNoEncontradoException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CashoutService {

    private final CashoutRepository repository;

    public CashoutService(CashoutRepository repository) {
        this.repository = repository;
    }

    /**
     * Crear un nuevo cashout.
     * @param cashout Objeto Cashout a guardar.
     * @return El cashout guardado.
     */
    public Mono<Cashout> crear(Cashout cashout) {
        return repository.save(cashout);
    }

    /**
     * Obtener todos los cashouts.
     * @return Flux de todos los cashouts.
     */
    public Flux<Cashout> obtener() {
        return repository.findAll();
    }

    /**
     * Obtener un cashout por ID.
     * @param id ID del cashout.
     * @return Mono del cashout encontrado o error si no existe.
     */
    public Mono<Cashout> obtenerPorId(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CashoutNoEncontradoException("Cashout no encontrado")));
    }

    /**
     * Obtener todos los cashouts de un usuario específico.
     * @param userId ID del usuario.
     * @return Flux de cashouts asociados al usuario.
     */
    public Flux<Cashout> obtenerPorUsuario(Long userId) {
        return repository.findByUserId(userId);
    }


}
