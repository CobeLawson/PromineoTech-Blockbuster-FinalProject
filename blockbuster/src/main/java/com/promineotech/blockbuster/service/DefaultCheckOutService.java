package com.promineotech.blockbuster.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.blockbuster.dao.CheckOutDao;
import com.promineotech.blockbuster.entity.Administrator;
import com.promineotech.blockbuster.entity.Customer;
import com.promineotech.blockbuster.entity.Product;
import com.promineotech.blockbuster.entity.Rental;
import com.promineotech.blockbuster.entity.RentalRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultCheckOutService implements CheckOutService {
	
	@Autowired
	private CheckOutDao checkOutDao;

	@Transactional(readOnly = true)
	@Override
	public List<Rental> fetchRentals(int administratorPK, int customerPK) {
		log.info("The fetchRentals method was called with administrator={} and customer={}", administratorPK, customerPK);
		
		List<Rental> rentals = checkOutDao.fetchRentals(administratorPK, customerPK);
		
		if(rentals.isEmpty()) {
			String msg = String.format("No rentals were found with administrator=%s and customer=%s", administratorPK, customerPK);
			throw new NoSuchElementException(msg);
		}
		
		return rentals;
	}
	
	@Transactional
	@Override
	public Rental createRental(RentalRequest rentalRequest) {
		log.info("The createRental method was called with rentalDraft={}", rentalRequest);
		Administrator administrator = getAdministrator(rentalRequest);
		log.info("Administrator was added to transaction.");
		log.info(administrator.getAdministratorName());
		Customer customer = getCustomer(rentalRequest);
		log.info("Customer was added to transaction.");
		log.info(customer.getCustomerName());
		List<Product> productList = getProducts(rentalRequest);
		log.info("Products were added to transaction.");
		LocalDate date = LocalDate.now();
		log.info("Date was added to transaction.");
		log.info(date.toString());
		LocalDate dueDate = date.plusDays(5);
		log.info("Due Date was added to transaction.");
		log.info(dueDate.toString());
		BigDecimal price = new BigDecimal("0.0");
		
		for(Product product : productList) {
			price = price.add(product.getPrice());
		}
		log.info("Price was added to transaction.");
		log.info(price.toString());
		
		return checkOutDao.saveRental(administrator, customer, productList, price, date, dueDate);
	}

	private List<Product> getProducts(RentalRequest rentalRequest) {
		return checkOutDao.fetchProducts(rentalRequest.getProductList());
	}

	private Customer getCustomer(RentalRequest rentalRequest) {
		return checkOutDao.fetchCustomer(rentalRequest.getCustomer())
				.orElseThrow(() -> new NoSuchElementException(
            "Customer with name=" + rentalRequest.getCustomer() + " was not found"));
	}

	private Administrator getAdministrator(RentalRequest rentalRequest) {
		return checkOutDao.fetchAdministrator(rentalRequest.getAdministrator())
				.orElseThrow(() -> new NoSuchElementException(
            "Administrator with name=" + rentalRequest.getAdministrator() + " was not found"));
	}

	@Override
	public void updateRental(int rentalPK, LocalDate dueDate) {
		log.info("The updateRentals method was called with rentalPK={} and dueDate={}", rentalPK, dueDate);
		checkOutDao.updateRental(rentalPK, dueDate);
	}

	@Override
	public void deleteRental(int rentalPK) {
		log.info("The deleteRental method was called with rentalPK={}", rentalPK);
		checkOutDao.deleteRental(rentalPK);
	}

}
