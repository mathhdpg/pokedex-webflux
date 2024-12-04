package com.webflux.pokedex.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webflux.pokedex.model.Pokemon;
import com.webflux.pokedex.model.PokemonEvent;
import com.webflux.pokedex.repository.PokemonRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    private final PokemonRepository pokemonRepository;

    public PokemonController(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @GetMapping
    public Flux<Pokemon> getAllPokemons() {
        return pokemonRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Pokemon>> getPokemon(@PathVariable("id") String id) {
        return pokemonRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound()
                        .build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pokemon> savePokemon(@RequestBody Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Pokemon>> updatePokemon(@PathVariable("id") String id, @RequestBody Pokemon pokemon) {
        return pokemonRepository.findById(id)
                .flatMap(existingPokemon -> {
                    existingPokemon.setName(pokemon.getName());
                    existingPokemon.setCategory(pokemon.getCategory());
                    existingPokemon.setHability(pokemon.getHability());
                    existingPokemon.setWeight(pokemon.getWeight());
                    return pokemonRepository.save(pokemon);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound()
                        .build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deletePokemon(@PathVariable("id") String id) {
        return pokemonRepository
                .findById(id)
                .flatMap(existingPokemon -> pokemonRepository
                        .delete(existingPokemon)
                        .<ResponseEntity<Void>>then(
                                Mono.just(
                                        ResponseEntity
                                                .ok()
                                                .build())))
                .defaultIfEmpty(
                        ResponseEntity
                                .notFound()
                                .build());
    }

    @DeleteMapping
    public Mono<Void> deleteAllPokemons() {
        return pokemonRepository.deleteAll();
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PokemonEvent> getPokemonEvents() {
        return Flux
                .interval(Duration.ofSeconds(5))
                .map(val -> new PokemonEvent(val, "Pokemons"));
    }
}