package com.promineotech.blockbuster.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import com.promineotech.blockbuster.entity.Administrator;
import com.promineotech.blockbuster.entity.Customer;
import com.promineotech.blockbuster.entity.Product;
import com.promineotech.blockbuster.entity.Rental;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Slf4j
@Component
public class DefaultCheckOutDao implements CheckOutDao {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	//Method for saving Rental Object and inserting into Transactions table
	@Override
	public Rental saveRental(Administrator administrator, Customer customer, List<Product> productList, BigDecimal price, LocalDate date, LocalDate dueDate) {
		SqlParams params = generateInsertSql(administrator, customer, price, date, dueDate);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(params.sql, params.source, keyHolder); 
		
		int rentalPK = keyHolder.getKey().intValue();
		log.info(String.valueOf(rentalPK));
		saveProducts(productList, rentalPK);

		updateAvailability(productList);
		updateDueDate(productList, dueDate);
		
		// @formatter:off
		return Rental.builder()
				.rentalPK(rentalPK)
				.administrator(administrator)
				.customer(customer)
				.productList(productList)
				.price(price)
				.date(date)
				.dueDate(dueDate)
				.build();
		// @formatter:on
	}

	//Method for creating list of Rental Objects from data in Transactions table that corresponds to certain combination of Administrator and Customer
	@Override
	public List<Rental> fetchRentals(int administratorPK, int customerPK) {
		log.info("DAO: administrator={}, customer={}", administratorPK, customerPK);
		
		// @formatter:off
		String sql = ""
				+ "SELECT * "
				+ "FROM transactions "
				+ "WHERE administrator_fk = :administrator_fk AND customer_fk = :customer_fk";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		Administrator administrator = fetchAdministratorByPK(administratorPK);
		log.info(administrator.getAdministratorName());
		params.put("administrator_fk", administrator.getAdministratorPK());
		Customer customer = fetchCustomerByPK(customerPK);
		log.info(customer.getCustomerName());
		params.put("customer_fk", customer.getCustomerPK());
		
		return jdbcTemplate.query(sql, params,
				new RowMapper<>() {
			
					//This method builds each individual Rental for the List
					@Override
					public Rental mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						// @formatter:off
						return Rental.builder()
								.rentalPK(rs.getInt("transaction_pk"))
								.administrator(administrator)
								.customer(customer)
								.productList(fetchProductList(rs.getInt("transaction_pk")))
								.price(rs.getBigDecimal("price"))
								.date(rs.getDate("transaction_date").toLocalDate())
								.dueDate(rs.getDate("due_date").toLocalDate())
								.build();
						// @formatter:on
					}

					//Nested method that creates the List of Products that were part of a specific Rental Transaction
					private List<Product> fetchProductList(int rentalPK) {
						// @formatter:off
						String sql = ""
								+ "SELECT * "
								+ "FROM product_list "
								+ "WHERE transaction_fk = :transaction_fk";
						// @formatter:on
						
						Map<String, Object> params = new HashMap<>();
						params.put("transaction_fk", rentalPK);
						
						return jdbcTemplate.query(sql, params,
								new RowMapper<>() {
									@Override
									public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
										
										return fetchProductByPK(rs.getInt("product_fk"));
										 
									}
						});
					}
		});
	}

	//Method that goes into Transactions table and updates specific transaction with new due date
	@Override
	public void updateRental(int rentalPK, LocalDate dueDate) {
		
		// @formatter:off
		String sql = ""
				+ "UPDATE transactions "
				+ "SET due_date = :due_date"
				+ "WHERE transaction_pk = :transaction_pk";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		params.put("due_date", dueDate);
		params.put("transaction_pk", rentalPK);
		
		jdbcTemplate.update(sql, params);
	}

	//Method for deleting a specific Rental from the Transaction table
	@Override
	public void deleteRental(int rentalPK) {
		
		// @formatter:off
		String sql = ""
				+ "DELETE FROM transactions "
				+ "WHERE transaction_pk = :transaction_pk";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		params.put("transaction_pk", rentalPK);
		
		jdbcTemplate.update(sql, params);
		
	}

	//Method for saving each product in the list of products into the product_list table
	void saveProducts(List<Product> productList, int rentalPK) {
		for(Product product : productList) {
			SqlParams params = generateInsertSql(product, rentalPK);
			jdbcTemplate.update(params.sql, params.source);
		}
		log.info("Product List is saved in database.");
	}
	
	//Method for building SQL query to save products from Rental into product_list table
	private SqlParams generateInsertSql(Product product, int rentalPK) {
		SqlParams params = new SqlParams();
		
		// @formatter:off
		params.sql = ""
				+ "INSERT INTO product_list ("
				+ "product_fk, transaction_fk"
				+ ") VALUES ("
				+ ":product_fk, :transaction_fk"
				+ ")";
		// @formatter:on
		
		params.source.addValue("product_fk", product.getProductPK());
		params.source.addValue("transaction_fk", rentalPK);
		
		return params;
	}

	//Method for building SQL query to insert new Rental with all parameters/fields into Transactions table
	private SqlParams generateInsertSql(Administrator administrator, Customer customer, BigDecimal price, LocalDate date, LocalDate dueDate) {
		
		// @formatter:off
		String sql = ""
				+ "INSERT INTO transactions ("
				+ "administrator_fk, customer_fk, price, transaction_date, due_date"
				+ ") VALUES ("
				+ ":administrator_fk, :customer_fk, :price, :transaction_date, :due_date"
				+")";
		// @formatter:on
		
		SqlParams params = new SqlParams();
		
		params.sql = sql;
		params.source.addValue("administrator_fk", administrator.getAdministratorPK());
		params.source.addValue("customer_fk", customer.getCustomerPK());
		params.source.addValue("price", price);
		params.source.addValue("transaction_date", date);
		params.source.addValue("due_date", dueDate);
		
		log.info("Transaction is entered into database.");
		
		return params;
	}
	
	//Method for pulling all products with specific IDs from products table to build list of Product Objects
	@Override
	public List<Product> fetchProducts(List<String> productIDs) {
		List<Product> productList = new ArrayList<Product>(productIDs.size());
				
		for(String productID : productIDs) {
			// @formatter:off
			String sql = ""
					+ "SELECT * "
					+ "FROM products "
					+ "WHERE product_id = :product_id";
			// @formatter:on
			
			Product product = null;
					
			Map<String, Object> params = new HashMap<>();
			params.put("product_id", productID);
			
			product = jdbcTemplate.query(sql, params, new ProductResultSetExtractor());
			log.info(product.getProductID());
			productList.add(product);
		}
		
		return productList;
		
	}
	
	//Method to update specific products in product table to set status to unavailable after being checked out
	public void updateAvailability(List<Product> productList) {
		for(Product product : productList) {
			// @formatter:off
			String sql = ""
					+ "UPDATE products "
					+ "SET status = 'UNAVAILABLE' "
					+ "WHERE product_pk = :product_pk";
			// @formatter:on
			
			Map<String, Object> params = new HashMap<>();
			params.put("product_pk", product.getProductPK());
		
			jdbcTemplate.update(sql, params);
			product.setStatus("UNAVAILABLE");
			log.info(product.getStatus());;
		}
	}


	//Method to update specific products in product table to set due date to five days after check out
	 private void updateDueDate(List<Product> productList, LocalDate dueDate) {
			for(Product product : productList) {
				// @formatter:off
				String sql = ""
						+ "UPDATE products "
						+ "SET due_date = :due_date "
						+ "WHERE product_pk = :product_pk";
				// @formatter:on
				
				Map<String, Object> params = new HashMap<>();
				params.put("due_date", dueDate);
				params.put("product_pk", product.getProductPK());
			
				jdbcTemplate.update(sql, params);
				product.setDueDate(dueDate);
				log.info(product.getDueDate().toString());;
			}
	}
	
	public Product fetchProductByPK(int productPK) {
		
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
	
	public Optional<Administrator> fetchAdministrator(String administrator) {
		
		// @formatter:off
		String sql = ""
				+ "SELECT * "
				+ "FROM administration "
				+ "WHERE administrator_name = :administrator_name";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		params.put("administrator_name", administrator);
		log.info(administrator);
		
		return Optional.ofNullable(
				jdbcTemplate.query(sql, params, new AdministratorResultSetExtractor()));
	}
	
	public Administrator fetchAdministratorByPK(int administratorPK) {
		
		// @formatter:off
		String sql = ""
				+ "SELECT * "
				+ "FROM administration "
				+ "WHERE administrator_pk = :administrator_pk";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		params.put("administrator_pk", administratorPK);
		log.info(String.valueOf(administratorPK));
		
		return jdbcTemplate.query(sql, params, new AdministratorResultSetExtractor());
	}

	public Optional<Customer> fetchCustomer(String customer) {
		
		// @formatter:off
		String sql = ""
				+ "SELECT * "
				+ "FROM customers "
				+ "WHERE customer_name = :customer_name";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		params.put("customer_name", customer);
		
		return Optional.ofNullable(
				jdbcTemplate.query(sql, params, new CustomerResultSetExtractor()));
	}

	public Customer fetchCustomerByPK(int customerPK) {
		
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

}
class ProductResultSetExtractor implements ResultSetExtractor<Product> {

	@Override
	public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
		rs.next();
		
		// @formatter:off
		return Product.builder()
				.productPK(rs.getInt("product_pk"))
				.productID(rs.getString("product_id"))
				.title(rs.getString("title"))
				.status(rs.getString("status"))
				.price(BigDecimal.valueOf(rs.getFloat("price")))
				.build();
		// @formatter:on
	}
	
}

class AdministratorResultSetExtractor implements ResultSetExtractor<Administrator> {

	@Override
	public Administrator extractData(ResultSet rs) throws SQLException, DataAccessException {
		rs.next();
		
		// @formatter:off
		return Administrator.builder()
				.administratorPK(rs.getInt("administrator_pk"))
				.administratorRole(rs.getString("administrator_role"))
				.administratorName(rs.getString("administrator_name"))
				.administratorPhone(rs.getString("administrator_phone"))
				.build();
		// @formatter:on
	}
	
}

class CustomerResultSetExtractor implements ResultSetExtractor<Customer> {

	@Override
	public Customer extractData(ResultSet rs) throws SQLException, DataAccessException {
		rs.next();
		
		// @formatter:off
		return Customer.builder()
				.customerPK(rs.getInt("customer_pk"))
				.customerName(rs.getString("customer_name"))
				.customerPhone(rs.getString("customer_phone"))
				.customerAge(rs.getInt("customer_age"))
				.build();
		// @formatter:on
	}
	
}

class SqlParams {
	String sql;
	MapSqlParameterSource source = new MapSqlParameterSource();
}
