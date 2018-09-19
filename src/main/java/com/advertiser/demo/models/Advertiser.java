package com.advertiser.demo.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class Advertiser implements Serializable {
	private static final long serialVersionUID = -1750922845778082911L;

	private String name;
	private String contact;
	private String credit;
	private String status;
	private String hasCredit;

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

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	@Override
	public String toString() {
		return this.name + ", " + this.contact + ", " + this.credit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHasCredit() {
		return hasCredit;
	}

	public void setHasCredit(String hasCredit) {
		this.hasCredit = hasCredit;
	}

}
