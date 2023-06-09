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
import com.promineotech.blockbuster.entity.Gameplay;
import com.promineotech.blockbuster.entity.Console;
import com.promineotech.blockbuster.entity.VideoGame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {
		"classpath:flyway/migrations/blockbuster.sql"},
		config = @SqlConfig(encoding = "utf-8"))
class FetchVideoGamesTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int serverPort;


	@Test
	void testThatVideoGamesAreReturnedWhenAValidGameplayAndConsoleAreSupplied() {
		//Given: valid genre, mediaType, and URI
		Gameplay gameplay = Gameplay.PLATFORMER;
		Console console = Console.PLAYSTATION_2;
		String uri = String.format("http://localhost:%d/video_games?gameplay=%s&console=%s", serverPort, gameplay, console);
		
		//When: connection is made to the URI
		log.info(uri);
		ResponseEntity<List<VideoGame>> response =
				restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
		log.info("List is sent.");
		
		//Then: a success status code (OK-200) is returned
		log.info(response.getStatusCode().toString());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		//And: the actual list returned is the same as the expected List
		List<VideoGame> actual = response.getBody();
		List<VideoGame> expected = buildExpected();
		
		assertThat(actual).isEqualTo(expected);
	}
	
	protected List<VideoGame> buildExpected() {
		List<VideoGame> list = new LinkedList<>();
		
		// @formatter:off
		list.add(VideoGame.builder()
				.videoGamePK(2001)
				.videoGameID("PS2_CRASH")
				.title("Crash Bandicoot")
				.gameplay(Gameplay.PLATFORMER)
				.console(Console.PLAYSTATION_2)
				.price(BigDecimal.valueOf(2.00))
				.build());
		
		list.add(VideoGame.builder()
				.videoGamePK(2003)
				.videoGameID("PS2_SPYRO")
				.title("Spyro the Dragon")
				.gameplay(Gameplay.PLATFORMER)
				.console(Console.PLAYSTATION_2)
				.price(BigDecimal.valueOf(2.00))
				.build());
		
		list.add(VideoGame.builder()
				.videoGamePK(2005)
				.videoGameID("PS2_JAK")
				.title("Jak and Daxter")
				.gameplay(Gameplay.PLATFORMER)
				.console(Console.PLAYSTATION_2)
				.price(BigDecimal.valueOf(2.00))
				.build());
		
		list.add(VideoGame.builder()
				.videoGamePK(2006)
				.videoGameID("PS2_JAK2")
				.title("Jak 2")
				.gameplay(Gameplay.PLATFORMER)
				.console(Console.PLAYSTATION_2)
				.price(BigDecimal.valueOf(2.00))
				.build());
		// @formatter:on
		
		return list;
	}

}
