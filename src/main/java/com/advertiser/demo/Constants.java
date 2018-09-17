package com.advertiser.demo;

public class Constants {

	public final static String DB_SELECT_FROM_ADVERTISER_BY_NAME = "SELECT * FROM advertiser WHERE name=?";
	public final static String DB_SELECT_FROM_ADVERTISER_ALL = "SELECT * FROM advertiser";
	public final static String DB_INSERT_INTO_ADVERTISER = "INSERT INTO advertiser values";
//	public final static String DB_CHECK_IF_PRIMARY_KEY_EXISTS = "SELECT * FROM ADVERTISER WHERE NAME=";
//public final static String DB_CHECK_IF_PRIMARY_KEY_EXISTS = "select * from advertiser where name=?";
//	public final static String DB_CHECK_IF_PRIMARY_KEY_EXISTS = "SELECT name FROM advertiser WHERE name='HEB'";
	public final static String DB_CHECK_IF_PRIMARY_KEY_EXISTS = "SELECT name FROM advertiser WHERE name=";

}
