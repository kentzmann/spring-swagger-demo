package com.example.demo.models;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * This is the base object response
 */
@Component
public class AdvertiserModel implements Serializable {
	private static final long serialVersionUID = 371023409122355394L;

	private String advertiserName;
	private String contactName;
	private String creditLimit;

	public String getAdvertiserName() {
		return advertiserName;
	}

	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}



}
