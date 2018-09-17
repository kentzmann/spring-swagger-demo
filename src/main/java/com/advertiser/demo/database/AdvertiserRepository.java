package com.advertiser.demo.database;

import com.advertiser.demo.Constants;
import com.advertiser.demo.models.Advertiser;
import com.advertiser.demo.models.AdvertiserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AdvertiserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Advertiser findByName(String name) {
		return jdbcTemplate.queryForObject(Constants.DB_SELECT_FROM_ADVERTISER_BY_NAME, new Object[] { name },
				new BeanPropertyRowMapper<>(Advertiser.class));
	}

	public List<Advertiser> findAllAdvertisers() {
		return jdbcTemplate.query(Constants.DB_SELECT_FROM_ADVERTISER_ALL,
				new BeanPropertyRowMapper<>(Advertiser.class));
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
