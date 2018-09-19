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
import java.util.*;

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
	public Advertiser findByName(String name) {
		LOGGER.info("GET ADVERTISER BY NAME: " + name);

		if (sqlObjectExists(name)) {
			return jdbcTemplate.queryForObject(Constants.DB_SELECT_FROM_ADVERTISER_BY_NAME, new Object[]{name},
					new BeanPropertyRowMapper<>(Advertiser.class));
		} else {
			Advertiser advertiser = new Advertiser();
			advertiser.setStatus(Constants.FAILED);
			return advertiser;
		}
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
	 * @param contact at Advertiser
	 * @param credit limit of Advertiser
	 * @return status of creation of Advertiser in DB
	 */
	public String createAdvertiserByName(String name, String contact, String credit) {
		if (sqlObjectExists(name)) {
			return Constants.FAILED;
		}
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		sb.append(Constants.DB_INSERT_INTO_ADVERTISER);
		formatter.format(" ('%s', '%s', '%s')", name, contact, credit);

		LOGGER.info("POST ADVERTISER WITH QUERY: " + sb.toString());
//		jdbcTemplate.execute(sb.toString());
		jdbcTemplate.execute(sb.toString());
		return Constants.SUCCESS;
	}

	/**
	 *
	 * @param advertiser JSON (raw data)
	 * @return status if record was updated
	 */
	public String updateAdvertiserByName1(Advertiser advertiser) {
		if (advertiser.getName().isEmpty()) {
			LOGGER.info("ADVERTISER NAME NOT FOUND IN RESPONSE BODY: "
					+ advertiser.getName());
			return Constants.FAILED;
		}

		LOGGER.info("UPDATE ADVERTISER BY NAME: " + "");
		//WORKS - have to pass in SQL and Object[] of params
		jdbcTemplate.update(Constants.DB_UPDATE_PRIMARY_KEY_WITH_VALUES,
				advertiser.getCredit(), advertiser.getName());
//		jdbcTemplate.update(Constants.DB_UPDATE_PRIMARY_KEY_WITH_VALUES,
//				params, advertiser.getName());
		return Constants.SUCCESS;
	}

	/**
	 *
	 * @param advertiser JSON (raw data)
	 * @return status if record was updated
	 */
	public String updateAdvertiserByName(Advertiser advertiser) {
		String name = advertiser.getName();
		if (name.isEmpty()) {
			LOGGER.info("ADVERTISER NAME NOT FOUND IN RESPONSE BODY: "
					+ advertiser.getName());
			return Constants.FAILED;
		}
		List<String> fieldsList = new LinkedList<>();
//		UPDATE advertiser SET contact=?,credit=? WHERE name=? ('hell', '666', 'HEB')
		String sql;
		String contact = advertiser.getContact();
		String credit = advertiser.getCredit();
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		if (!contact.isEmpty() && !credit.isEmpty()) {
			sb.append("UPDATE advertiser SET contact=?,credit=? WHERE name=?");
			formatter.format(" '%s', '%s', '%s'", contact, credit, name);
			sql = "UPDATE advertiser SET contact=?,credit=? WHERE name=? ";
			fieldsList.add(contact);
			fieldsList.add(credit);
		} else if (!contact.isEmpty()) {
			sb.append("UPDATE advertiser SET contact=? WHERE name=?");
			formatter.format(" ('%s', '%s')", contact, name);
			fieldsList.add(contact);
		} else if (!credit.isEmpty()) {
			sb.append("UPDATE advertiser SET credit=? WHERE name=?");
			formatter.format(" ('%s', '%s')", credit, name);
			fieldsList.add(credit);
		}
		Object[] fieldsObjects = fieldsList.toArray();
		LOGGER.info("DEBUG SQL: " + sb.toString());

//		LOGGER.info("DEBUG: " + createUpdateSqlString(name, contact, credit));
//		String sql = "UPDATE advertiser SET contact = ? , "
//				+ "credit = ? "
//				+ "WHERE name = ?";
//		Object[] records = {advertiser.getName(), advertiser.getCredit(), null};
//		String sql = "INSERT INTO advertiser (contact, credit) VALUES ('Kent', '999') WHERE name='HEB'";
		for (Object s : fieldsObjects) {
			LOGGER.info("DEBUG OBJECT: " + s);
		}
//		jdbcTemplate.update(sql +  " values ('hell', '666', 'HEB')");//name, contact, credit));
		jdbcTemplate.update(sb.toString(), fieldsObjects);
		return Constants.SUCCESS;
	}

//	public Optional<Object> getBlog(long id) {
//		if (!found) {
//			return Optional.empty();
//		}
//		return Optional.of(blog);
//	}

//	private String createUpdateSqlString(String name, String contact, String credit) {
//
////		return sb.toString();
//	}


	//Another way to create an Advertiser by name
	//Note: change the Autowired global variable at the top to:
	//private NamedParameterJdbcTemplate jdbcTemplate;
//	public String createAdvertiserByName(Advertiser advertiser) {
//		if (advertiser.getName().isEmpty()) {
//			LOGGER.info("ADVERTISER NAME NOT FOUND IN RESPONSE BODY: "
//					+ advertiser.getName());
//			return Constants.FAILED;
//		}
//
//		String sql = "INSERT INTO advertiser " +
//				"(name, contact, credit) VALUES (:name, :contact, :credit)";
//
//		Map<String, Object> parameters = new HashMap<>();
//		parameters.put("contact", advertiser.getContact());
//		parameters.put("credit", advertiser.getCredit());
//		parameters.put("name", advertiser.getName());
//
//		jdbcTemplate.update(sql, parameters);
//		return Constants.SUCCESS;
//	}

	private String buildUpdateQueryString(Advertiser advertiser) {
//		String optionalQuery = "UPDATE advertiser SET ('%s','%s') VALUES ('%s','%s') WHERE name=?";
		String contact = advertiser.getContact();
		String credit = advertiser.getCredit();
		Map<String, String> updateMap = new LinkedHashMap<>();
		if (!contact.isEmpty()) {
			updateMap.put("contact", contact);
		}
		if (!credit.isEmpty()) {
			updateMap.put("credit", credit);
		}
		String pre = "UPDATE advertiser SET (";
		Iterator it = updateMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			String optionalQuery = ") VALUES ('%s','%s') WHERE name=?";
			StringBuilder sb = new StringBuilder();
			Formatter formatter = new Formatter(sb);
			formatter.format(optionalQuery, advertiser.getContact(), advertiser.getCredit(),
					advertiser.getContact(), advertiser.getCredit());
		}
		return null;
	}

	//WORKING
//	public String updateAdvertiserByName(String name, String contact, String credit) {
//		Advertiser advertiser = new Advertiser();
//		Map<String, String> paramsToUpdate = new HashMap<>(createUpdateMap(name, contact, credit));
//		if (!sqlObjectExists(name) || paramsToUpdate.isEmpty()) {
//			return Constants.FAILED;
//		}
//		LOGGER.info("UPDATE ADVERTISER WITH QUERY: " + Constants.DB_UPDATE_PRIMARY_KEY_WITH_VALUES);
//		jdbcTemplate.update(Constants.DB_UPDATE_PRIMARY_KEY_WITH_VALUES, contact, credit, name);
//		return Constants.SUCCESS;
//	}

	private Map<String, String> createUpdateMap(String name, String contact, String credit) {
		Map<String, String> paramsToUpdate = new HashMap<>();
		if (!credit.isEmpty()) {
			paramsToUpdate.put("credit", credit);
		}
		if (!contact.isEmpty()) {
			paramsToUpdate.put("contact", contact);
		}
		if (!name.isEmpty()) {
			paramsToUpdate.put("name", name);
		}
		return paramsToUpdate;
	}

	/**
	 * @param name of Advertiser
	 * @return true if found in DB
	 */
	private boolean sqlObjectExists(String name) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		sb.append(Constants.DB_CHECK_IF_PRIMARY_KEY_EXISTS);
		formatter.format("'%s'", name);

		// anonymous function could be turned into Lambda expression
		List<String> strLst = jdbcTemplate.query(sb.toString(), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultSet, int i) throws SQLException {
				return resultSet.getString(1);
			}
		});
		return !strLst.isEmpty();
	}

//	private boolean sqlObjectExists(String sql) {
//
//		// anonymous function could be turned into Lambda expression
//		List<String> strLst = jdbcTemplate.query(sql, new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet resultSet, int i) throws SQLException {
//				return resultSet.getString(1);
//			}
//		});
//		return strLst.isEmpty();
//	}

//WIP to get jdbcTemplate autowire to not show IDE error
//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource ds = new DriverManagerDataSource();
//		ds.setDriverClassName("org.h2.Driver");
//		ds.setUrl("jdbc:h2:tcp://localhost/~/spitter");
//		ds.setUsername("sa");
//		ds.setPassword("");
//		return ds;
//	}
//
//	@Bean
//	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//		return new JdbcTemplate(dataSource);
//	}

}
