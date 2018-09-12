package com.advertiser.demo;

import com.advertiser.demo.controllers.AdvertiserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class AdvertiserApiApplicationTests {

	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	@Test
	public void testAdvertiserControllerHasValidGet() {
		AdvertiserController advertiserController = new AdvertiserController();
//		AdvertiserModel result = advertiserController.getAdvertiser();


//		assert(result);
	}
//	public void contextLoads() {
//		@SuppressWarnings("rawtypes")
//		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
//				"http://localhost:" + this.port + "/hello-world", Map.class);
//
//		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}

}
