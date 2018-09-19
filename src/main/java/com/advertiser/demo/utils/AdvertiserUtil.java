package com.advertiser.demo.utils;

import com.advertiser.demo.models.Advertiser;

import java.util.LinkedList;
import java.util.List;

/**
 * Build some mock data before database is created
 * Does validation on our REST controller URI values
 */
public class AdvertiserUtil {

	private AdvertiserUtil() {}

	// Using data.sql in place of this method
	public static List<Advertiser> buildAdvertiserDemoData() {
		List<Advertiser> advertisers = new LinkedList<>();
		Advertiser advertiser1 = new Advertiser();
		advertiser1.setName("HEB");
		advertiser1.setContact("Mike Georgoff");
		advertiser1.setCredit("300000");
		advertisers.add(advertiser1);

		Advertiser advertiser2 = new Advertiser();
		advertiser2.setName("COTA");
		advertiser2.setContact("Brian Mishkin");
		advertiser2.setCredit("500000");
		advertisers.add(advertiser2);

		Advertiser advertiser3 = new Advertiser();
		advertiser3.setName("Chevrolet");
		advertiser3.setContact("Tom Bolton");
		advertiser3.setCredit("5000000");
		advertisers.add(advertiser3);

		return advertisers;
	}

	public static boolean isAdvertiserInputValid() {
		return false;
	}

}
