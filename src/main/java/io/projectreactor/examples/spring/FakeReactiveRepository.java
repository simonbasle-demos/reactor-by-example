package io.projectreactor.examples.spring;

import io.projectreactor.examples.Sir;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * A fake incomplete Spring Data {@link ReactiveCrudRepository} to naively demonstrate
 * a reactive findOne.
 *
 * @author Simon Baslé
 */
@Repository
public class FakeReactiveRepository implements ReactiveCrudRepository<Sir, String> {

	@Override
	public Mono<Sir> findOne(String s) {
		return Mono.just(s)
		           .then(id -> {
			           if ("notfound".equalsIgnoreCase(s))
				           return Mono.empty();
			           else
				           return Mono.just(new Sir(s, "GUY"));
		           });
	}

	//== NOT IMPLEMENTED ==
	@Override
	public <S extends Sir> Mono<S> save(S s) {
		return null;
	}

	@Override
	public <S extends Sir> Flux<S> save(Iterable<S> iterable) {
		return null;
	}

	@Override
	public <S extends Sir> Flux<S> save(Publisher<S> publisher) {
		return null;
	}

	@Override
	public Mono<Sir> findOne(Mono<String> s) {
		return null;
	}

	@Override
	public Mono<Boolean> exists(String s) {
		return null;
	}

	@Override
	public Mono<Boolean> exists(Mono<String> mono) {
		return null;
	}

	@Override
	public Flux<Sir> findAll() {
		return null;
	}

	@Override
	public Flux<Sir> findAll(Iterable<String> iterable) {
		return null;
	}

	@Override
	public Flux<Sir> findAll(Publisher<String> publisher) {
		return null;
	}

	@Override
	public Mono<Long> count() {
		return null;
	}

	@Override
	public Mono<Void> delete(String s) {
		return null;
	}

	@Override
	public Mono<Void> delete(Sir sir) {
		return null;
	}

	@Override
	public Mono<Void> delete(Iterable<? extends Sir> iterable) {
		return null;
	}

	@Override
	public Mono<Void> delete(Publisher<? extends Sir> publisher) {
		return null;
	}

	@Override
	public Mono<Void> deleteAll() {
		return null;
	}
}
