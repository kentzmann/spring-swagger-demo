package com.advertiser.demo;

public class Constants {
	public final static String SUCCESS = "success";
	public final static String FAILED = "failed";
	public final static String HAS_CREDIT_YES = "hasCredit";
	public final static String HAS_CREDIT_LOW = "lowCredit";
	public final static String HAS_CREDIT_NO = "noCredit";
	public final static String DB_SELECT_FROM_ADVERTISER_BY_NAME = "SELECT * FROM advertiser WHERE name=?";
	public final static String DB_SELECT_FROM_ADVERTISER_ALL = "SELECT * FROM advertiser";
	public final static String DB_INSERT_INTO_ADVERTISER = "INSERT INTO advertiser values";
	public final static String DB_CHECK_IF_PRIMARY_KEY_EXISTS = "SELECT name FROM advertiser WHERE name=";
	public final static String DB_UPDATE_PRIMARY_KEY_CONTACT_CREDIT = "UPDATE advertiser SET contact=?,credit=? WHERE name=?";
	public final static String DB_UPDATE_PRIMARY_KEY_CREDIT = "UPDATE advertiser SET credit=? WHERE name=?";
	public final static String DB_UPDATE_PRIMARY_KEY_CONTACT = "UPDATE advertiser SET contact=? WHERE name=?";
	public final static String DB_DELETE_FROM_ADVERTISER_BY_NAME = "DELETE FROM advertiser WHERE name=?";
}
