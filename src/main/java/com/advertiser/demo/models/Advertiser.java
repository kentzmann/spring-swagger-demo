package com.advertiser.demo.models;


import java.io.Serializable;

public class Advertiser implements Serializable {
	private static final long serialVersionUID = -1750922845778082911L;

	private String name;
	private String contact;
	private String credit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCreditLimit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
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
