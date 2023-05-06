package com.promineotech.blockbuster.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.promineotech.blockbuster.entity.Customer;

@Service
@Component
public class DefaultCustomerSystemDao implements CustomerSystemDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Customer> fetchCustomers() {
		
		// @formatter:off
		String sql = ""
				+ "SELECT * "
				+ "FROM customers";
		// @formatter:on
		
		return jdbcTemplate.query(sql, 
				new RowMapper<>() {
					@Override
					public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						// @formatter:off
						return Customer.builder()
								.customerPK(rs.getInt("customer_pk"))
								.customerName(rs.getString("customer_name"))
								.customerAge(rs.getInt("customer_age"))
								.customerPhone(rs.getString("customer_phone"))
								.build();
						// @formatter:on
					}
			}
		);
	}

}
