package com.advertiser.demo;

import com.advertiser.demo.controllers.AdvertiserController;
import com.advertiser.demo.database.AdvertiserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cglib.core.Converter;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(properties = {"management.port=0"}) //error
public class AdvertiserApiApplicationTests {
//	@Value("${local.management.port}")
//	private int mgt;

	private static final Logger LOGGER = LogManager.getLogger(Converter.class);

	private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_UTF8_VALUE;
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private AdvertiserController controller;

	@Autowired
	private AdvertiserRepository advertiserRepository;

	@Autowired
	private TestRestTemplate testRestTemplate;

//	@Autowired
//	private TestEntityManager entityManager; //error

	@LocalServerPort
	private int port = 8080;

	@Before
	public void setup() throws Exception {
		LOGGER.info("	JUNIT: START NEW TEST");
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	public void contextLoads() throws Exception {
		LOGGER.info("	JUNIT: CONTEXT LOADS");
		assertThat(controller).isNotNull();
	}

	/**
	 * action: GET Advertisers
	 * result: advertisers is not empty
	 */
	@Test
	public void shouldGetAllAdvertisersObject() throws Exception {
		LOGGER.info("	JUNIT: GET ALL ADVERTISERS RETURNS 200 OK STATUS");
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/api/advertiser/advertisers", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	/**
	 * action: GET Advertiser name
	 * result: name does DB exist
	 */
	@Test
	public void shouldGetAdvertiserNameObject() throws Exception {
		String goodInput = "Cadillac";
		LOGGER.info("	JUNIT: GET ADVERTISER BY NAME \"" + goodInput + "\" RETURNS 200 OK STATUS");
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/api/advertiser/" + goodInput, Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	/**
	 * action: GET Advertiser name
	 * result: name in DB is same as input
	 */
	@Test
	public void shouldGetSameAdvertiserObjectNameAsInput() throws Exception {
		String goodInput = "Cadillac";
		LOGGER.info("	JUNIT: GET ADVERTISER BY NAME \"" + goodInput + "\" RETURNS SAME VALUE FROM DATABASE");
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/api/advertiser/" + goodInput, Map.class);

		then(entity.getBody().get("name").equals(goodInput));
	}

	/**
	 * action: GET Advertiser name
	 * result: name in DB does NOT exist
	 */
	@Test
	public void shouldReturn500StatusForNonExistingAdvertiser() throws Exception {
		String badInput = "Caddy";
		LOGGER.info("	JUNIT: GET ADVERTISER BY NAME \"" + badInput + "\" RETURNS 500 SERVER ERROR");
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/api/advertiser/" + badInput, Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * action: GET Advertiser name
	 * result: status object should say failed
	 */
	@Test
	public void shouldGetFailedObjectForNonExistingAdvertiser() throws Exception {
		String badInput = "Caddy";
		LOGGER.info("	JUNIT: GET ADVERTISER BY NAME \"" + badInput + "\" RETURNS FAILED STATUS");
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/api/advertiser/" + badInput, Map.class);

		LOGGER.info("	JUNIT: DEBUG GET BODY" + entity.getBody().toString());
		then(entity.getBody().get("status").equals("failed"));
	}

	/**
	 * action: POST Advertiser object
	 * result: Advertiser with unique name should work
	 */
	@Test
	public void shouldPostNewUniqueAdvertiser() throws Exception {
		String goodInput = "X-Games";
		LOGGER.info("	JUNIT: POST ADVERTISER BY UNIQUE NAME \"" + goodInput + "\" RETURNS 200 OK STATUS");
		// create JSON body
		Map<String, String> body = new HashMap<>();
		body.put("name", goodInput);
		body.put("contact", "Tony Hawk");
		body.put("credit", "99999");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> responseEntity = testRestTemplate.postForEntity(
				"http://localhost:" + this.port + "/api/advertiser/newAdvertiser", entity, Map.class, 1);

		then(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	/**
	 * action: POST Advertiser object
	 * result: Advertiser with duplicate name should NOT work
	 */
	@Test
	public void shouldNotPostDuplicateAdvertiser() throws Exception {
		String badInput = "HEB";
		LOGGER.info("	JUNIT: POST ADVERTISER BY UNIQUE NAME \"" + badInput + "\" RETURNS 500 SERVER ERROR");
		// create JSON body
		Map<String, String> body = new HashMap<>();
		body.put("name", badInput);
		body.put("contact", "Duplicate Name");
		body.put("credit", "000000");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> responseEntity = testRestTemplate.postForEntity(
				"http://localhost:" + this.port + "/api/advertiser/newAdvertiser", entity, Map.class, 1);

		then(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * action: PUT Advertiser object
	 * result: existing Advertiser should be updated
	 */
	@Test
	public void shouldPutExistingAdvertiser() throws Exception {
		String goodInput = "Chevrolet";
		LOGGER.info("	JUNIT: PUT ADVERTISER BY EXISTING NAME \"" + goodInput + "\" RETURNS 200 OK STATUS");
		// create JSON body
		Map<String, String> body = new HashMap<>();
		body.put("name", goodInput);
		body.put("contact", "Kent");
		body.put("credit", "10000000");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

		@SuppressWarnings("rawtypes")
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/advertiser/updateAdvertiser", HttpMethod.PUT, entity, String.class);

		then(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}


	/**
	 * action: PUT Advertiser object
	 * result: non-existing Advertiser should NOT be updated
	 */
	@Test
	public void shouldNotPutNonExistingAdvertiser() throws Exception {
		String badInput = "Audi";
		LOGGER.info("	JUNIT: POST ADVERTISER BY NON-EXISTING NAME \"" + badInput + "\" RETURNS 500 SERVER ERROR");
		// create JSON body
		Map<String, String> body = new HashMap<>();
		body.put("name", badInput);
		body.put("contact", "Adam");
		body.put("credit", "0000");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

		@SuppressWarnings("rawtypes")
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/advertiser/updateAdvertiser", HttpMethod.PUT, entity, String.class);

		then(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * action: DELETE Advertiser name
	 * result: advertiser should be deleted
	 */
	@Test
	public void shouldDeleteExistingAdvertiserObject() throws Exception {
		String goodInput = "COTA";
		LOGGER.info("	JUNIT: DELETE ADVERTISER BY NAME \"" + goodInput + "\" RETURNS 200 OK STATUS");
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/advertiser/" + goodInput, HttpMethod.DELETE, HttpEntity.EMPTY, Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	/**
	 * action: DELETE Advertiser name
	 * result: advertiser should NOT be deleted
	 */
	@Test
	public void shouldDeleteNonExistingAdvertiserObject() throws Exception {
		String badInput = "BMW";
		LOGGER.info("	JUNIT: DELETE ADVERTISER BY NAME \"" + badInput + "\" RETURNS 500 SERVER ERROR");
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/advertiser/" + badInput, HttpMethod.DELETE, HttpEntity.EMPTY, Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * action: GET Advertiser credit
	 * result: advertiser should have credit
	 */
	@Test
	public void shouldGetAdvertiserStatusHasCredit() throws Exception {
		String goodInput = "Cadillac";
		LOGGER.info("	JUNIT: GET ADVERTISER CREDIT \"" + goodInput + "\" RETURNS HAS CREDIT");
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/api/advertiser/" + goodInput, Map.class);

		then(entity.getBody().get("name").equals(Constants.HAS_CREDIT_YES));
	}

	/**
	 * action: GET Advertiser credit
	 * result: advertiser should have low credit
	 */
	@Test
	public void shouldGetAdvertiserStatusDoesNotMuchCredit() throws Exception {
		String goodInput = "Google";
		LOGGER.info("	JUNIT: GET ADVERTISER CREDIT \"" + goodInput + "\" RETURNS LOW CREDIT");
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/api/advertiser/" + goodInput, Map.class);

		then(entity.getBody().get("name").equals(Constants.HAS_CREDIT_LOW));
	}

	/**
	 * action: GET Advertiser credit
	 * result: advertiser should have no credit
	 */
	@Test
	public void shouldGetAdvertiserStatusDoesNotHaveCredit() throws Exception {
		String goodInput = "COTA";
		LOGGER.info("	JUNIT: GET ADVERTISER CREDIT \"" + goodInput + "\" RETURNS LOW CREDIT");
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/api/advertiser/" + goodInput, Map.class);

		then(entity.getBody().get("name").equals(Constants.HAS_CREDIT_NO));
	}

}
