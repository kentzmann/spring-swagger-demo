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
 * 		GET, POST, PUT, DELETE name name
 * Advertiser Resources:
 * 		Name, Contact, Credit
 * Validate if Advertiser has enough credit with:
 * 		GET name name & credit
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
	 * @param name Name
	 * @return Advertiser object by Name
	 */
	@GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final ResponseEntity<Advertiser> getAdvertiser(@PathVariable(name = "name") String name) {
		Advertiser advertiserResponse = advertiserRepository.findByName(name);
		return new ResponseEntity<>(advertiserResponse, HttpStatus.OK);
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

	/**
	 * Add a new Advertiser in database
	 * @param name Name
	 * @return status
	 */
	@PostMapping(value = "/{name}/{contact}/{credit}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Advertiser> newAdvertiser(@PathVariable(name = "name") String name,
								@PathVariable(name = "contact") String contact,
								@PathVariable(name = "credit") String credit) {
		Advertiser advertiserResponse = new Advertiser();
		advertiserResponse.setStatus(advertiserRepository.putAdvertiserByName(name, contact, credit));
		return new ResponseEntity<>(advertiserResponse, HttpStatus.OK);
	}


	//Credit limit
//	@GetMapping(value = "/{name}/credit", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	return name.stillHasCredit


}
