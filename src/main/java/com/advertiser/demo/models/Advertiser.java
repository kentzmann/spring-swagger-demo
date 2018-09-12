package com.advertiser.demo.models;


import java.io.Serializable;

public class Advertiser implements Serializable {
	private static final long serialVersionUID = -1750922845778082911L;

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

	//TODO
//	private String status;
//	private String error;

//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public String getError() {
//		return error;
//	}
//
//	public void setError(String error) {
//		this.error = error;
//	}

}
