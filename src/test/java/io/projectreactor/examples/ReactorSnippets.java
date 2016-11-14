package io.projectreactor.examples;

import java.util.Arrays;
import java.util.List;

import com.vgrazi.play.PriceTick;
import com.vgrazi.play.SomeFeed;
import com.vgrazi.play.SomeListener;
import org.junit.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

public class ReactorSnippets {

	private static List<String> words = Arrays.asList("the",
			"quick",
			"brown",
			"fox",
			"jumped",
			"over",
			"the",
			"lazy",
			"dog");

	@Test
	public void simpleCreation() {
		Flux<String> fewWords = Flux.just("Hello", "World");
		Flux<String> manyWords = Flux.fromIterable(words);

		fewWords.subscribe(System.out::println);
		System.out.println();
		manyWords.subscribe(System.out::println);
	}

	@Test
	public void countingLetters() {
		Flux<String> manyLetters = Flux
				.fromIterable(words)
				.flatMap(word -> Flux.fromArray(word.split("")))
				.zipWith(Flux.range(1, Integer.MAX_VALUE),
						(string, count) -> String.format("%2d. %s", count, string));

		manyLetters.subscribe(System.out::println);
	}

	@Test
	public void findingMissingLetter() {
		Flux<String> manyLetters = Flux
				.fromIterable(words)
				.flatMap(word -> Flux.fromArray(word.split("")))
				.distinct()
				.sort()
				.zipWith(Flux.range(1, Integer.MAX_VALUE),
						(string, count) -> String.format("%2d. %s", count, string));

		manyLetters.subscribe(System.out::println);
	}

	@Test
	public void restoreMissingLetter() {
		Mono<String> missing = Mono.just("s");
		Flux<String> allLetters = Flux
				.fromIterable(words)
				.flatMap(word -> Flux.fromArray(word.split("")))
				.concatWith(missing)
				.distinct()
				.sort()
				.zipWith(Flux.range(1, Integer.MAX_VALUE),
						(string, count) -> String.format("%2d. %s", count, string));

		allLetters.subscribe(System.out::println);
	}

	@Test
	public void shortCircuit() {
		Flux<String> helloPauseWorld = Mono.just("Hello")
		                                   .concatWith(Mono.just("world").delaySubscriptionMillis(500));
		helloPauseWorld.subscribe(System.out::println);
	}

	@Test
	public void blocks() {
		Flux<String> helloPauseWorld = Mono.just("Hello")
		                                   .concatWith(Mono.just("world").delaySubscriptionMillis(5000));
		helloPauseWorld.toStream()
		               .forEach(System.out::println);
	}

	@Test
	public void firstEmitting() {
		Mono<String> a = Mono.just("oops I'm late")
		                     .delaySubscriptionMillis(450);
		Flux<String> b = Flux.just("let's get", "the party", "started")
		                     .delayMillis(400);

		Flux.firstEmitting(a, b)
		    .toIterable()
		    .forEach(System.out::println);
	}
}
