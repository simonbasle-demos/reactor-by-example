package io.projectreactor.examples;

import java.time.Duration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;

/**
 * @author Simon Baslé
 */
@Component
public class MyReactiveLibrary {

	public Flux<String> alphabet5(char from) {
		int rangeSize = Math.min(5, 'z' - from + 1);
		return Flux.range((int) from, rangeSize)
		           .map(i -> "" + (char) i.intValue());
	}

	public Flux<String> alphabet5fixed(char from) {
		return Flux.range((int) from, 5)
		           .map(i -> "" + (char) i.intValue())
		           .take(Math.min(5, 'z' - from + 1));
	}

	public Mono<String> withDelay(String value, int delaySeconds) {
		return Mono.just(value)
		           .delaySubscription(Duration.ofSeconds(delaySeconds));
	}
}
