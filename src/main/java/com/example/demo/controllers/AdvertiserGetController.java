package com.example.demo.controllers;

import com.example.demo.models.AdvertiserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is where we delegate GET responses
 */
@RestController
public class AdvertiserGetController {

	@Autowired
	private AdvertiserModel advertiserModel;

	@GetMapping("/api/advertiser")
//	@RequestMapping("/api/advertiser")
	public AdvertiserModel getAdvertiser() {
		advertiserModel.setAdvertiserName("HEB");
		advertiserModel.setContactName("Linsey");
		advertiserModel.setCreditLimit("30000");

		return advertiserModel;
	}

}
