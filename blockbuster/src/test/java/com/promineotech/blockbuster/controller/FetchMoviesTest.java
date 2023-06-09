package com.promineotech.blockbuster.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.List;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.blockbuster.entity.Genre;
import com.promineotech.blockbuster.entity.MediaType;
import com.promineotech.blockbuster.entity.Movie;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {
		"classpath:flyway/migrations/blockbuster.sql"},
		config = @SqlConfig(encoding = "utf-8"))
class FetchMoviesTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int serverPort;


	@Test
	void testThatMoviesAreReturnedWhenAValidGenreAndMediaAreSupplied() {
		//Given: valid genre, mediaType, and URI
		Genre genre = Genre.DRAMA;
		MediaType mediaType = MediaType.BLURAY;
		String uri = String.format("http://localhost:%d/movies?genre=%s&mediaType=%s", serverPort, genre, mediaType);
		
		//When: connection is made to the URI
		log.info(uri);
		ResponseEntity<List<Movie>> response =
				restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
		log.info("List is sent.");
		
		//Then: a success status code (OK-200) is returned
		log.info(response.getStatusCode().toString());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		//And: the actual list returned is the same as the expected List
		List<Movie> actual = response.getBody();
		List<Movie> expected = buildExpected();
		
		assertThat(actual).isEqualTo(expected);
	}
	
	protected List<Movie> buildExpected() {
		List<Movie> list = new LinkedList<>();
		
		// @formatter:off
		list.add(Movie.builder()
				.moviePK(1002)
				.movieID("BR_SHAWSHANK")
				.title("The Shawshank Redemption")
				.genre(Genre.DRAMA)
				.mediaType(MediaType.BLURAY)
				.price(BigDecimal.valueOf(4.00))
				.build());

		list.add(Movie.builder()
				.moviePK(1071)
				.movieID("BR_LAMB")
				.title("Lamb")
				.genre(Genre.DRAMA)
				.mediaType(MediaType.BLURAY)
				.price(BigDecimal.valueOf(4.00))
				.build());

		list.add(Movie.builder()
				.moviePK(1074)
				.movieID("BR_GOODFELLAS")
				.title("Goodfellas")
				.genre(Genre.DRAMA)
				.mediaType(MediaType.BLURAY)
				.price(BigDecimal.valueOf(4.00))
				.build());

		list.add(Movie.builder()
				.moviePK(1083)
				.movieID("BR_WOLFWALLSTREET")
				.title("The Wolf of Wall Street")
				.genre(Genre.DRAMA)
				.mediaType(MediaType.BLURAY)
				.price(BigDecimal.valueOf(4.00))
				.build());
		// @formatter:on
		
		return list;
	}

}
