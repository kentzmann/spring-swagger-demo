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

	public static List<Advertiser> buildAdvertiserDemoData() {
		List<Advertiser> advertisers = new LinkedList<>();
		Advertiser advertiser1 = new Advertiser();
		advertiser1.setAdvertiserName("HEB");
		advertiser1.setContactName("Mike Georgoff");
		advertiser1.setCreditLimit("300000");
		advertisers.add(advertiser1);

		Advertiser advertiser2 = new Advertiser();
		advertiser2.setAdvertiserName("CircuitOfAmericas");
		advertiser2.setContactName("Brian Mishkin");
		advertiser2.setCreditLimit("500000");
		advertisers.add(advertiser2);

		Advertiser advertiser3 = new Advertiser();
		advertiser3.setAdvertiserName("Chevrolet");
		advertiser3.setContactName("Tom Bolton");
		advertiser3.setCreditLimit("5000000");
		advertisers.add(advertiser3);

		return advertisers;
	}

	public static boolean isAdvertiserNameValid() {
		return false;
	}

}
