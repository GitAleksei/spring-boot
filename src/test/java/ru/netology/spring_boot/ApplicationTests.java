package ru.netology.spring_boot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;

	public static GenericContainer<?> devapp = new GenericContainer<>("devapp")
			.withExposedPorts(8080);

	public static GenericContainer<?> prodapp = new GenericContainer<>("prodapp")
			.withExposedPorts(8081);

	@BeforeAll
	public static void setUp() {
		devapp.start();
		prodapp.start();
	}

	@Test
	void devappTestResponseBody() {
		String expected = "Current profile is dev";

		ResponseEntity<String> forEntity =
				restTemplate.getForEntity(
						"http://localhost:" + devapp.getMappedPort(8080) + "/profile",
						String.class);
		String actual = forEntity.getBody();
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void prodappTestResponseBody() {
		String expected = "Current profile is production";

		ResponseEntity<String> forEntity =
				restTemplate.getForEntity(
						"http://localhost:" + prodapp.getMappedPort(8081) + "/profile",
						String.class);
		String actual = forEntity.getBody();
		Assertions.assertEquals(expected, actual);
	}
}
