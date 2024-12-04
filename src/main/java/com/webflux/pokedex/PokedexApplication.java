package com.webflux.pokedex;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import com.webflux.pokedex.model.Pokemon;
import com.webflux.pokedex.repository.PokemonRepository;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class PokedexApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ReactiveMongoOperations operations, PokemonRepository pokemonRepository) {
		return args -> {
			Flux<Pokemon> pokemonFlux = Flux.just(
					new Pokemon("1", "Bulbasaur", "Grass", "Overgrow", new BigDecimal("6.9")),
					new Pokemon("2", "Ivysaur", "Grass", "Overgrow", new BigDecimal("13.0")),
					new Pokemon("3", "Venusaur", "Grass", "Overgrow", new BigDecimal("100.0")),
					new Pokemon("4", "Charmander", "Fire", "Blaze", new BigDecimal("8.5")),
					new Pokemon("5", "Charmeleon", "Fire", "Blaze", new BigDecimal("19.0")),
					new Pokemon("6", "Charizard", "Fire", "Blaze", new BigDecimal("90.5")),
					new Pokemon("7", "Squirtle", "Water", "Torrent", new BigDecimal("9.0")),
					new Pokemon("8", "Wartortle", "Water", "Torrent", new BigDecimal("22.5")),
					new Pokemon("9", "Blastoise", "Water", "Torrent", new BigDecimal("85.5")),
					new Pokemon("10", "Caterpie", "Bug", "Shed Skin", new BigDecimal("2.9")))
					.flatMap(pokemonRepository::save);

			pokemonFlux
					.thenMany(pokemonRepository.findAll())
					.subscribe(System.out::println);
		};
	}

}
