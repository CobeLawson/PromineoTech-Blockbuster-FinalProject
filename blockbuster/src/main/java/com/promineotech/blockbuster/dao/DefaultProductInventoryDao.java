package com.promineotech.blockbuster.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.promineotech.blockbuster.entity.Product;
import lombok.extern.slf4j.Slf4j;

@Service
@Component
@Slf4j
public class DefaultProductInventoryDao implements ProductInventoryDao {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Product> fetchProducts(String status) {
		log.info("DAO: status={}", status);
		
		// @formatter:off
		String sql = ""
				+ "SELECT * "
				+ "FROM products "
				+ "WHERE status = :status";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		log.info(status);
		params.put("status", status);
		
		return jdbcTemplate.query(sql, params,
				new RowMapper<>() {
					@Override
					public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						// @formatter:off
						return Product.builder()
								.productPK(rs.getInt("product_pk"))
								.productID(rs.getString("product_id"))
								.title(rs.getString("title"))
								.price(BigDecimal.valueOf(rs.getInt("price")))
								.status(rs.getString("status"))
								.build();
						// @formatter:on
					}
		}
				);
	}

}
