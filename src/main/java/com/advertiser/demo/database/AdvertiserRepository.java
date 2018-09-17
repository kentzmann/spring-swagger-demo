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

	public Advertiser findByName(String name) {

		LOGGER.info("DB QUERY - GET ADVERTISER BY NAME: " + name);
		return jdbcTemplate.queryForObject(Constants.DB_SELECT_FROM_ADVERTISER_BY_NAME, new Object[] { name },
				new BeanPropertyRowMapper<>(Advertiser.class));
	}

	public List<Advertiser> findAllAdvertisers() {

		LOGGER.info("DB QUERY - GET ALL ADVERTISERS");
		return jdbcTemplate.query(Constants.DB_SELECT_FROM_ADVERTISER_ALL,
				new BeanPropertyRowMapper<>(Advertiser.class));
	}

	public String putAdvertiserByName(String name, String contact, String credit) {

		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		sb.append(Constants.DB_CHECK_IF_PRIMARY_KEY_EXISTS);
		formatter.format("'%s'", name);

		LOGGER.info("DB QUERY - CHECK IF ADVERTISER EXISTS: " + sb.toString());
//		String sqlObject = jdbcTemplate.queryForObject(sb.toString(), String.class);
//		LOGGER.info("	rowCount: " + sqlObject);
		// check DB if primary key of name exists
//		if (!sqlObject.isEmpty()) {
		if (!sqlObjectIsUnique(sb.toString())) {
			return "failed";
		}

		sb.setLength(0);
		sb.append(Constants.DB_INSERT_INTO_ADVERTISER);
		formatter.format(" ('%s', '%s', '%s')", name, contact, credit);

		LOGGER.info("DB QUERY - PUT ADVERTISER: " + sb.toString());
		jdbcTemplate.execute(sb.toString());
		return "success";
	}

	private boolean sqlObjectIsUnique(String sql) {
		List<String> strLst = jdbcTemplate.query(sql, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultSet, int i) throws SQLException {
				return resultSet.getString(1);
			}
		});
		return strLst.isEmpty();
	}

//WIP to get jdbcTemplate autowire to work
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
