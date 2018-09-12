package com.advertiser.demo.controllers;

import com.advertiser.demo.models.AdvertiserModel;
import com.advertiser.demo.utils.AdvertiserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Advertiser to perform with:
 * 		GET, POST, PUT, DELETE advertiser name
 * Advertiser Resources:
 * 		Name, Contact, Credit Limit
 * Validate if Advertiser has enough credit with:
 * 		GET advertiser name & credit
 */
@RestController
@RequestMapping("/api")
public class AdvertiserController {

	@Autowired
	private AdvertiserModel advertiserModel;

	@RequestMapping("/advertisers")
	public AdvertiserModel getAdvertisers() {
		advertiserModel.setAdvertisers(AdvertiserUtil.buildAdvertiserDemoData());
		return advertiserModel;
	}

	// Return data from Advertiser name
  	@GetMapping(value = "/{advertiser}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> getAdvertiser(@PathVariable String advertiser) {
		return new ResponseEntity<>("HEB", HttpStatus.OK);
	}

	@PostMapping("/{advertiser}")
	public AdvertiserModel putAdvertiser(@PathVariable String advertiserName) {
		return advertiserModel;
	}

	//Credit limit
	//@GetMapping(value = "/{advertiser}/{credit}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	//return advertiser.stillHasCredit

}
