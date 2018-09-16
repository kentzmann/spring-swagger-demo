package com.advertiser.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * THIS FILE DOES NOT ALLOW SCHEMA.SQL TO INSERT VALUES
 */
@Entity
public class Advertiserz {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String contact;
	private String credit;

	//TODO find out if I need these
//	public String getContactName() {
//		return contactName;
//	}
//
//	public void setContactName(String contactName) {
//		this.contactName = contactName;
//	}
//
//	public String getCreditLimit() {
//		return creditLimit;
//	}
//
//	public void setCreditLimit(String creditLimit) {
//		this.creditLimit = creditLimit;
//	}

}
