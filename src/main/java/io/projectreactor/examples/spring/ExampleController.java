package io.projectreactor.examples.spring;

import io.projectreactor.examples.MyReactiveLibrary;
import io.projectreactor.examples.Sir;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Simon Baslé
 */
@Controller
public class ExampleController {

	private final MyReactiveLibrary reactiveLibrary;

	public ExampleController(@Autowired MyReactiveLibrary reactiveLibrary) {
		this.reactiveLibrary = reactiveLibrary;
	}

	@RequestMapping("hello/{who}")
	@ResponseBody
	public Mono<String> hello(@PathVariable String who) {
		return Mono.just(who)
		           .map(w -> "Hello " + w + "!");
	}

	@RequestMapping("helloDelay/{who}")
	@ResponseBody
	public Mono<String> helloDelay(@PathVariable String who) {
		return reactiveLibrary.withDelay("Hello " + who + "!!", 2);
	}

	@RequestMapping(value = "heyMister", method = RequestMethod.POST)
	@ResponseBody
	public Flux<String> hey(@RequestBody Mono<Sir> body) {
		return Mono.just("Hey mister ")
				.concatWith(body
						.flatMap(sir -> Flux.fromArray(sir.getLastName().split("")))
						.map(String::toUpperCase)
						.take(1)
				).concatWith(Mono.just(". how are you?"));
	}
}
