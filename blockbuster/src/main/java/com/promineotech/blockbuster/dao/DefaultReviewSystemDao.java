package com.promineotech.blockbuster.dao;

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
import com.promineotech.blockbuster.entity.Customer;
import com.promineotech.blockbuster.entity.Product;
import com.promineotech.blockbuster.entity.Review;
import lombok.extern.slf4j.Slf4j;

@Service
@Component
@Slf4j
public class DefaultReviewSystemDao implements ReviewSystemDao {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Review> fetchReviews(int productPK) {
		log.info("DAO: productPK={}", productPK);
		
		// @formatter:off
		String sql = ""
				+ "SELECT * "
				+ "FROM reviews "
				+ "WHERE product_fk = :product_fk";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		log.info(String.valueOf(productPK));
		params.put("product_fk", productPK);
		
		return jdbcTemplate.query(sql, params,
				new RowMapper<>() {
					@Override
					public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						// @formatter:off
						return Review.builder()
										.reviewPK(rs.getInt("review_pk"))
										.reviewString(rs.getString("review_string"))
										.product(fetchProductByPK(rs.getInt("product_fk")))
										.customer(fetchCustomerByPK(rs.getInt("customer_fk")))
										.build();
						// @formatter:on
					}

					private Customer fetchCustomerByPK(int customerPK) {
						
						// @formatter:off
						String sql = ""
								+ "SELECT * "
								+ "FROM customers "
								+ "WHERE customer_pk = :customer_pk";
						// @formatter:on
						
						Map<String, Object> params = new HashMap<>();
						params.put("customer_pk", customerPK);
						
						return jdbcTemplate.query(sql, params, new CustomerResultSetExtractor());
					}

					private Product fetchProductByPK(int productPK) {
						
						// @formatter:off
						String sql = ""
								+ "SELECT * "
								+ "FROM products "
								+ "WHERE product_pk = :product_pk";
						// @formatter:on
						
						Map<String, Object> params = new HashMap<>();
						params.put("product_pk", productPK);
						
						return jdbcTemplate.query(sql, params, new ProductResultSetExtractor());
					}
		}
				);
	}

}
