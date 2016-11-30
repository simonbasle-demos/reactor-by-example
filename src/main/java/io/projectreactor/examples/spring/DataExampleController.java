package io.projectreactor.examples.spring;

import io.projectreactor.examples.Sir;
import reactor.core.publisher.Mono;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Simon Baslé
 */
@RestController
public class DataExampleController {

	private final ReactiveCrudRepository<Sir, String> reactiveRepository;

	//Note Spring Boot 4.3+ autowires single constructors now
	public DataExampleController(ReactiveCrudRepository<Sir, String> repo) {
		this.reactiveRepository = repo;
	}

	@GetMapping("data/{who}")
	public Mono<ResponseEntity<Sir>> hello(@PathVariable String who) {
		return reactiveRepository.findOne(who)
		                         .map(ResponseEntity::ok)
		                         .defaultIfEmpty(ResponseEntity.status(404).body(null));
	}
}
