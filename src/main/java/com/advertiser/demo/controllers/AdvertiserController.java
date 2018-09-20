package com.advertiser.demo.controllers;

import com.advertiser.demo.Constants;
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

import java.util.List;

/**
 * Controller for Advertiser to perform with:
 * 		GET, POST, PUT, DELETE name
 * Advertiser Resources:
 * 		Name, Contact, Credit
 * Validate if Advertiser has enough credit with:
 * 		GET name & credit
 * note: @EnableWebMvc causes Swagger to not load
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
	 * @param name of Advertiser
	 * @return Advertiser object or status
	 */
	@GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public final ResponseEntity<Advertiser> getAdvertiser(@PathVariable(name = "name") String name) {
		Advertiser advertiserResponse = new Advertiser();
		if (advertiserRepository.validateAdvertiserExists(name)) {
			return new ResponseEntity<>(advertiserRepository.getAdvertiserByName(name), HttpStatus.OK);
		} else {
			advertiserResponse.setStatus(Constants.FAILED);
			return new ResponseEntity<>(advertiserResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Delete Advertiser in database
	 * @param name of Advertiser
	 * @return status
	 */
	@DeleteMapping(value = "{name}")
	public final ResponseEntity<Advertiser> deleteAdvertiser(@PathVariable(name = "name") String name) {
		Advertiser advertiserResponse = new Advertiser();
		if (advertiserRepository.validateAdvertiserExists(name)) {
			advertiserResponse.setStatus(advertiserRepository.deleteAdvertiserByName(name));
			return new ResponseEntity<>(advertiserResponse, HttpStatus.OK);
		} else {
			advertiserResponse.setStatus(Constants.FAILED);
			return new ResponseEntity<>(advertiserResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Find all Advertisers in database
	 * @return Advertisers array or status
	 */
	@RequestMapping(value = "/advertisers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<AdvertiserModel> getAdvertisers() {
		AdvertiserModel advertiserModel = new AdvertiserModel();
		List<Advertiser> advertisers = advertiserRepository.findAllAdvertisers();
		if (!advertisers.isEmpty()) {
			advertiserModel.setAdvertisers(advertisers);
			return new ResponseEntity<>(advertiserModel, HttpStatus.OK);
		} else {
			advertiserModel.setStatus(Constants.FAILED);
			return new ResponseEntity<>(advertiserModel, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Create new Advertiser
	 * @param advertiser JSON object
	 * @return status
	 */
	@PostMapping(value = "/newAdvertiser")
	public ResponseEntity<Advertiser> newAdvertiser(@RequestBody Advertiser advertiser) {
		Advertiser advertiserResponse = new Advertiser();
		if (advertiserRepository.isInputObjectValid(advertiser)) {
			advertiserResponse.setStatus(advertiserRepository.createAdvertiserByName(advertiser));
			return new ResponseEntity<>(advertiserResponse, HttpStatus.OK);
		} else {
			advertiserResponse.setStatus(Constants.FAILED);
			return new ResponseEntity<>(advertiserResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Update existing Advertiser
	 * @param advertiser JSON object
	 * @return status
	 */
	@PutMapping(value = "/updateAdvertiser")
	public ResponseEntity<Advertiser> updateAdvertiser(@RequestBody Advertiser advertiser) {
		Advertiser advertiserResponse = new Advertiser();
		if (advertiserRepository.isUpdateObjectValid(advertiser)) {
			advertiserResponse.setStatus(advertiserRepository.updateAdvertiserByName(advertiser));
			return new ResponseEntity<>(advertiserResponse, HttpStatus.OK);
		} else {
			advertiserResponse.setStatus(Constants.FAILED);
			return new ResponseEntity<>(advertiserResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Find if Advertiser has sufficient credit
	 * @return status or credit
	 */
	@GetMapping(value = "/credit/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Advertiser> getAdvertiserHasCredit(@PathVariable(name = "name") String name) {
		Advertiser advertiserResponse = new Advertiser();
		if (advertiserRepository.validateAdvertiserExists(name)) {
			advertiserResponse.setStatus(advertiserRepository.getAdvertiserCreditStatus(name));
			return new ResponseEntity<>(advertiserResponse, HttpStatus.OK);
		} else {
			advertiserResponse.setStatus(Constants.FAILED);
			return new ResponseEntity<>(advertiserResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
