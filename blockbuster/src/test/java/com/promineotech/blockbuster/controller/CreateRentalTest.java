package com.promineotech.blockbuster.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.blockbuster.entity.Rental;
import com.promineotech.blockbuster.entity.Administrator;
import com.promineotech.blockbuster.entity.Customer;
import com.promineotech.blockbuster.entity.Product;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {
		"classpath:flyway/migrations/blockbuster.sql"},
		config = @SqlConfig(encoding = "utf-8"))
class CreateRentalTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int serverPort;
	
	@Test
	void testFetchRentalsGivenAdministratorAndCustomer() {
		//Given: valid administrator, customer, URI
		Administrator administrator = createAdministrator();
		Customer customer = createCustomer();
		int administratorPK = 8;
		int customerPK = 5;
		List<Product> productList = createProductList();
		String uri = String.format("http://localhost:%d/transactions?administratorPK=%s&customerPK=%s", serverPort, String.valueOf(administratorPK), String.valueOf(customerPK));
		
		//When: connection is made to the URI
		ResponseEntity<List<Rental>> response =
				restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
		
		//Then: a success status code (OK-200) is returned
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		//And: the actual list returned is the same as the expected list
		List<Rental> actual = response.getBody();
		List<Rental> expected = buildExpected(administrator, customer, productList);
		
		assertThat(actual).isEqualTo(expected);
		
	}

	private Administrator createAdministrator() {
		return Administrator.builder()
						.administratorPK(8)
						.administratorRole("Cashier")
						.administratorName("Kathryn Bigelow")
						.administratorPhone("999-888-7777")
						.build();
	}

	private Customer createCustomer() {
		return Customer.builder()
						.customerPK(5)
						.customerName("Margot Robbie")
						.customerAge(32)
						.customerPhone("567-678-7890")
						.build();
	}

	private List<Product> createProductList() {
		List<Product> productList = new LinkedList<>();
		
		// @formatter:off
		productList.add(Product.builder()
				.productPK(1001)
				.productID("4K_SHAWSHANK")
				.title("The Shawshank Redemption")
				.price(BigDecimal.valueOf(5.00))
				.dueDate(null)
				.status("UNAVAILABLE")
				.build());
		
		productList.add(Product.builder()
				.productPK(1010)
				.productID("BR_STARDUST")
				.title("Stardust")
				.price(BigDecimal.valueOf(4.00))
				.dueDate(null)
				.status("UNAVAILABLE")
				.build());
		// @formatter:on
		
		return productList;
	}

	private List<Rental> buildExpected(Administrator administrator, Customer customer, List<Product> productList) {
		List<Rental> list = new LinkedList<>();
		
		// @formatter:off
		list.add(Rental.builder()
				.rentalPK(1)
				.administrator(administrator)
				.customer(customer)
				.date(LocalDate.parse("2023-02-27"))
				.dueDate(LocalDate.parse("2023-03-01"))
				.price(BigDecimal.valueOf(9.00))
				.productList(productList)
				.build());
		// @formatter:on
		
		
		return list;
	}
	

	@Test
	void testCreateRentalReturnsSuccess201() {
		// Given: a rental as JSON
		String body = createRentalBody();
		String uri = String.format("http://localhost:%d/transactions", serverPort);
		log.info(uri);
		HttpHeaders headers = new HttpHeaders();
  	headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> bodyEntity = new HttpEntity<>(body, headers);
		log.info("Rental is created.");
		log.info(bodyEntity.toString());
		
		// When: the rental is sent
		ResponseEntity<Rental> response = 
				restTemplate.exchange(uri, HttpMethod.POST, bodyEntity, Rental.class);
		log.info("Rental is sent.");
		
		// Then: a 201 status is returned
		log.info(response.getStatusCode().toString());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
		log.info("Status is returned.");
		
		// And: the returned rental is correct		
		Rental rental = response.getBody();
		log.info(rental.getAdministrator().getAdministratorName());
		assertThat(rental.getAdministrator().getAdministratorName()).isEqualTo("David Lynch");
		log.info(rental.getCustomer().getCustomerName());
		assertThat(rental.getCustomer().getCustomerName()).isEqualTo("Keanu Reeves");
		log.info(rental.getDate().toString());
		assertThat(rental.getDate().toString()).isEqualTo("2023-05-06");
		log.info(rental.getDueDate().toString());
		assertThat(rental.getDueDate().toString()).isEqualTo("2023-05-11");
		log.info(rental.getPrice().toString());
		assertThat(rental.getPrice().toString()).isEqualTo("9.0");
		assertThat(rental.getProductList()).hasSize(2);		
		
		log.info("Rental is correct.");
		
	}

	protected String createRentalBody() {
		// @formatter:off
		return "{\r\n"
				+ " \"administrator\":\"David Lynch\",\r\n"
				+ " \"customer\":\"Keanu Reeves\",\r\n"
				+ " \"date\":\"2023-05-06\",\r\n"
				+ " \"dueDate\":\"2023-05-11\",\r\n"
				+ " \"price\":\"9.00\",\r\n"
				+ " \"productList\":[\r\n"
						+" \"BR_GOODFELLAS\",\r\n"
						+" \"NSWITCH_DARKSOULS\"\r\n"
					+"]\r\n"
				+ "}";
		// @formatter:on
	}

}
