package com.advertiser.demo.database;

import com.advertiser.demo.Constants;
import com.advertiser.demo.models.Advertiser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Converter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.List;

/**
 * Executes Stored Procedures to Database
 */
@Repository
public class AdvertiserRepository {

	private static final Logger LOGGER = LogManager.getLogger(Converter.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * @param name of Advertiser
	 * @return Advertiser object in DB
	 */
	public Advertiser getAdvertiserByName(String name) {
//		if (sqlObjectExists(name)) {
			return jdbcTemplate.queryForObject(Constants.DB_SELECT_FROM_ADVERTISER_BY_NAME, new Object[]{name},
					new BeanPropertyRowMapper<>(Advertiser.class));
//		} else {
//			Advertiser advertiser = new Advertiser();
//			advertiser.setStatus(Constants.FAILED);
//			LOGGER.info("GET ADVERTISER WAS NOT FOUND FOR NAME: " + name);
//			return advertiser;
//		}
	}

	//WIP
	public boolean validateAdvertiserExists(String name) {
		if (!sqlObjectExists(name)) {
			LOGGER.info("GET ADVERTISER WAS NOT FOUND FOR NAME: " + name);
			return false;
		}
		return true;
	}

	/**
	 * @return All Advertisers in DB
	 */
	public List<Advertiser> findAllAdvertisers() {
		LOGGER.info("GET ALL ADVERTISERS");
		return jdbcTemplate.query(Constants.DB_SELECT_FROM_ADVERTISER_ALL,
				new BeanPropertyRowMapper<>(Advertiser.class));
	}

	/**
	 * @param name of Advertiser
	 * @return status if Advertiser found or has credit
	 */
	public String findIfAdvertiserHasCredit(String name) {
		if (name == null || !sqlObjectExists(name)) {
			LOGGER.info("ADVERTISER NAME DOES NOT EXIST");
			return Constants.FAILED;
		}
		Advertiser advertiser = getAdvertiserByName(name);
		if (advertiser.getCredit().isEmpty()
				|| Double.parseDouble(advertiser.getCredit()) == 0) {
			LOGGER.info("ADVERTISER HAS NOT OR EMPTY CREDIT");
			return Constants.HAS_CREDIT_NO;
		} else if (Double.parseDouble(advertiser.getCredit()) < 1000.0) {
			LOGGER.info("ADVERTISER HAS LOW CREDIT BELOW 1000");
			return Constants.HAS_CREDIT_LOW;
		}
		LOGGER.info("ADVERTISER HAS SUFFICIENT CREDIT");
		return Constants.HAS_CREDIT_YES;
	}

	/**
	 * @param advertiser to create (raw JSON data)
	 * @return status if record was created
	 */
	public String createAdvertiserByName(Advertiser advertiser) {
		if (advertiser.getName() == null
				|| sqlObjectExists(advertiser.getName())
				|| advertiser.getContact() == null || advertiser.getCredit() == null) {
			LOGGER.info("ADVERTISER ALREADY EXISTS OR CONTACT OR CREDIT MISSING");
			return Constants.FAILED;
		}
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		sb.append(Constants.DB_INSERT_INTO_ADVERTISER);
		formatter.format(" ('%s', '%s', '%s')",
				advertiser.getName(), advertiser.getContact(), advertiser.getCredit());

		LOGGER.info("POST ADVERTISER WITH QUERY: " + sb.toString());
		// note: could not get dynamic SQL String working with Object[], Map<String, String>, or String params
		jdbcTemplate.execute(sb.toString());
		return Constants.SUCCESS;
	}

	/**
	 * @param name of advertiser to delete
	 * @return status
	 */
	public String deleteAdvertiserByName(String name) {
		if (name.isEmpty() || !sqlObjectExists(name)) {
			LOGGER.info("DELETE ADVERTISER WAS NOT FOUND FOR NAME: " + name);
			return Constants.FAILED;
		}
		LOGGER.info("DELETE ADVERTISER BY NAME: " + name);
		jdbcTemplate.update(Constants.DB_DELETE_FROM_ADVERTISER_BY_NAME, name);
		return Constants.SUCCESS;
	}

	/**
	 * @param advertiser to update (raw JSON data)
	 * @return status if record was updated
	 */
	public String updateAdvertiserByName(Advertiser advertiser) {
		if (advertiser.getName() == null
				|| !sqlObjectExists(advertiser.getName())) {
			LOGGER.info("ADVERTISER NAME NOT FOUND IN RESPONSE BODY");
			return Constants.FAILED;
		}
		// note: could not get dynamic SQL String working with Object[], Map<String, String>, or String params
		if (advertiser.getContact() != null && advertiser.getCredit() != null) {
			LOGGER.info("ADVERTISER CONTACT AND CREDIT UPDATED");
			jdbcTemplate.update(Constants.DB_UPDATE_PRIMARY_KEY_CONTACT_CREDIT
					, advertiser.getContact(), advertiser.getCredit(), advertiser.getName());
		} else if (advertiser.getContact() != null) {
			LOGGER.info("ADVERTISER CONTACT UPDATED");
			jdbcTemplate.update(Constants.DB_UPDATE_PRIMARY_KEY_CONTACT
					, advertiser.getContact(), advertiser.getName());
		} else if (advertiser.getCredit() != null) {
			LOGGER.info("ADVERTISER CREDIT UPDATED");
			jdbcTemplate.update(Constants.DB_UPDATE_PRIMARY_KEY_CREDIT
					, advertiser.getCredit(), advertiser.getName());
		}
		return Constants.SUCCESS;
	}

	/**
	 * @param name of Advertiser
	 * @return true if found in DB
	 */
	public boolean sqlObjectExists(String name) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		sb.append(Constants.DB_CHECK_IF_PRIMARY_KEY_EXISTS);
		formatter.format("'%s'", name);

		// note: anonymous function could be turned into Lambda expression
		List<String> strLst = jdbcTemplate.query(sb.toString(), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultSet, int i) throws SQLException {
				return resultSet.getString(1);
			}
		});
		return !strLst.isEmpty();
	}

}
