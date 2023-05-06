package com.promineotech.blockbuster.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.promineotech.blockbuster.entity.Administrator;
import com.promineotech.blockbuster.entity.Customer;
import com.promineotech.blockbuster.entity.Product;
import com.promineotech.blockbuster.entity.Rental;

//DAO interface for CRUD for Rental Transactions
public interface CheckOutDao {
	Rental saveRental(Administrator administrator, Customer customer, List<Product> productList, BigDecimal price, LocalDate date, LocalDate dueDate);
	List<Rental> fetchRentals(int administratorPK, int customerPK);
	void updateRental(int rentalPK, LocalDate dueDate);
	void deleteRental(int rentalPK);
	Product fetchProductByPK(int productPK);
	List<Product> fetchProducts(List<String> productIDs);
	Optional<Administrator> fetchAdministrator(String administrator);
	Optional<Customer> fetchCustomer(String customer);
}
