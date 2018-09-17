package com.advertiser.demo.controllers;

import com.advertiser.demo.database.AdvertiserRepository;
import com.advertiser.demo.models.Advertiser;
import com.advertiser.demo.models.AdvertiserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Advertiser to perform with:
 * 		GET, POST, PUT, DELETE advertiser name
 * Advertiser Resources:
 * 		Name, Contact, Credit
 * Validate if Advertiser has enough credit with:
 * 		GET advertiser name & credit
 * Note: @EnableWebMvc causes Swagger to not load
 */
@RestController
@RequestMapping("/api/advertiser")
public class AdvertiserController {

	private static final Logger LOGGER = LogManager.getLogger(Converter.class);

	@Autowired
	private AdvertiserModel advertiserModel;

	@Autowired
	private AdvertiserRepository advertiserRepository;

	/**
	 * Find Advertiser in database
	 * @param advertiser Name
	 * @return Advertiser object by Name
	 */
	@GetMapping(value = "/{advertiser}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final ResponseEntity<Advertiser> getAdvertiser(@PathVariable(name = "advertiser") String advertiser) {
		Advertiser advertiserResponse = advertiserRepository.findByName(advertiser);
		LOGGER.info("GET ADVERTISER BY NAME: " + advertiserResponse.toString());
		return new ResponseEntity<>(advertiserResponse, HttpStatus.OK);
	}

	/**
	 * Add a new Advertiser in database
	 * @param advertiser Name
	 * @return status
	 */
	@PostMapping(value = "/{advertiser}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String newAdvertiser(@PathVariable(name = "advertiser") String advertiser) {
		LOGGER.info("POST ADVERTISER BY NAME: " + advertiser);

		return advertiser;
	}

	/**
	 * Fin all Advertisers in database
	 * @return all Advertisers
	 */
	@RequestMapping(value = "/advertisers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public AdvertiserModel getAdvertisers() {
		AdvertiserModel advertiserModel = new AdvertiserModel();
		advertiserModel.setAdvertisers(advertiserRepository.findAllAdvertisers());
		return advertiserModel;
	}

	//Credit limit
//	@GetMapping(value = "/{advertiser}/credit", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	return advertiser.stillHasCredit


}
