package com.promineotech.blockbuster.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.blockbuster.entity.Rental;
import com.promineotech.blockbuster.entity.RentalRequest;
import com.promineotech.blockbuster.service.CheckOutService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultCheckOutController implements CheckOutController {

	@Autowired
	private CheckOutService checkOutService;

	@Override
	public List<Rental> fetchRentals(int administratorPK, int customerPK) {
		log.info("administrator={} and customer={}", administratorPK, customerPK);
		return checkOutService.fetchRentals(administratorPK, customerPK);
	}
	
	@Override
	public Rental createRental(RentalRequest rentalRequest) {
		log.info("rentalRequest={}", rentalRequest);
		return checkOutService.createRental(rentalRequest);
	}

	@Override
	public void updateRental(int rentalPK, LocalDate dueDate) {
		log.info("rentalPK={}, dueDate={}", rentalPK, dueDate);
		checkOutService.updateRental(rentalPK, dueDate);
	}

	@Override
	public void deleteRental(int rentalPK) {
		log.info("rentalPK={}", rentalPK);
		checkOutService.deleteRental(rentalPK);
	}

}
