package com.advertiser.demo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * This is the base object response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class AdvertiserModel implements Serializable {
	private static final long serialVersionUID = 371023409122355394L;

	private List<Advertiser> advertisers;

	public List<Advertiser> getAdvertisers() {
		return advertisers;
	}

	public void setAdvertisers(List<Advertiser> advertisers) {
		this.advertisers = advertisers;
	}
}
