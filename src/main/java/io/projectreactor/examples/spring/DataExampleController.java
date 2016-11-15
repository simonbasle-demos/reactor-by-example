package io.projectreactor.examples.spring;

import io.projectreactor.examples.Sir;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Simon Baslé
 */
@Controller
public class DataExampleController {

	private final ReactiveCrudRepository<Sir, String> reactiveRepository;

	public DataExampleController(@Autowired ReactiveCrudRepository<Sir, String> repo) {
		this.reactiveRepository = repo;
	}

	@RequestMapping("data/{who}")
	@ResponseBody
	public Mono<ResponseEntity<Sir>> hello(@PathVariable String who) {
		return reactiveRepository.findOne(who)
		                         .map(ResponseEntity::ok)
		                         .defaultIfEmpty(ResponseEntity.status(404).body(null));
	}
}
