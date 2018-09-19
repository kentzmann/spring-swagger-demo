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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

	@Test
	public void shouldReturn200ForGetAllAdvertisers() throws Exception {
		LOGGER.info("	JUNIT: GET ALL ADVERTISERS RETURNS 200 OK STATUS");
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/api/advertiser/advertisers", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	//TODO - refactor to use status codes
	@Test
	public void shouldReturn200ForGetAdvertiserByName() throws Exception {
		LOGGER.info("	JUNIT: GET ADVERTISER BY NAME \"Cadillac\" RETURNS 200 OK STATUS");
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/api/advertiser/Cadillac", Map.class);


		LOGGER.info("	JUNIT: DEBUG: " + entity.getBody().get("name"));
//		then(entity.getBody().get("name").equals("Cadillac"));
		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

//	@Test
//	public void testAdvertiserControllerHasValidGet() {
//		AdvertiserController advertiserController = new AdvertiserController();
//		Advertiser result = advertiserController.getAdvertiser();
//
//
//		assert(result);
//	}
//	public void contextLoads() {
//		@SuppressWarnings("rawtypes")
//		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
//				"http://localhost:" + this.port + "/hello-world", Map.class);
//
//		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}

}
