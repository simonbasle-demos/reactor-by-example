package io.projectreactor.examples.spring;

import io.projectreactor.examples.Sir;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testHello() {
		ResponseEntity<String> entity =
				this.restTemplate.getForEntity("/hello/{who}", String.class, "Walter");

		assertThat(entity.getBody()).isEqualToIgnoringCase("Hello Walter!");
	}

	@Test
	public void testHelloDelay() {
		ResponseEntity<String> entity =
				this.restTemplate.getForEntity("/helloDelay/{who}", String.class, "Walter");

		assertThat(entity.getBody()).isEqualToIgnoringCase("Hello Walter!!");
	}

	@Test
	public void testHeyMister() {
		ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(
				"/heyMister",
				new Sir("Walter", "tEST"),
				String.class);

		assertThat(responseEntity.getBody()).isEqualTo("Hey mister T. how are you?");
	}

	@Test
	public void testDataFound() {
		ResponseEntity<Sir> responseEntity =
				this.restTemplate.getForEntity("/data/{who}", Sir.class, "buddy");

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		assertThat(responseEntity.getBody()).isEqualTo(new Sir("buddy", "GUY"));
	}

	@Test
	public void testDataNotFound() {
		ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(
				"/data/{who}",
				String.class,
				"notFound");

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
		assertThat(responseEntity.getBody()).isNull();
	}

}
