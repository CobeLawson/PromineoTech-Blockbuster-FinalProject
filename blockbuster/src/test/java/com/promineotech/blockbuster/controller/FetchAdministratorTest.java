package com.promineotech.blockbuster.controller;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.promineotech.blockbuster.entity.Administrator;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {
		"classpath:flyway/migrations/blockbuster.sql"},
		config = @SqlConfig(encoding = "utf-8"))
class FetchAdministratorTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int serverPort;


	@Test
	void testThatAdministratorsAreReturnedWhenSuppliedValidRole() {
		//Given: Valid role and URI
		String role = "Assistant Manager";
		String uri = String.format("http://localhost:%d/administration?administratorRole=%s", serverPort, role);
	
		//When: connection is made to the URI
		log.info(uri);
		ResponseEntity<List<Administrator>> response =
				restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
		log.info("List is sent.");
		
		//Then: a success status code (OK-200) is returned
		log.info(response.getStatusCode().toString());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		//And: the actual list returned is the same as the expected List
		List<Administrator> actual = response.getBody();
		List<Administrator> expected = buildExpected();
		
		assertThat(actual).isEqualTo(expected);
		
	}


	private List<Administrator> buildExpected() {
		List<Administrator> list = new LinkedList<>();
		
		list.add(Administrator.builder()
				.administratorPK(3)
				.administratorName("Steven Spielberg")
				.administratorRole("Assistant Manager")
				.administratorPhone("333-444-5555")
				.build());
		
		list.add(Administrator.builder()
				.administratorPK(4)
				.administratorName("Wes Anderson")
				.administratorRole("Assistant Manager")
				.administratorPhone("444-555-6666")
				.build());
		
		list.add(Administrator.builder()
				.administratorPK(5)
				.administratorName("Quentin Tarantino")
				.administratorRole("Assistant Manager")
				.administratorPhone("555-666-7777")
				.build());
		
		return list;
	}

}
