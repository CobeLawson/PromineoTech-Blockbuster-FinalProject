package com.promineotech.blockbuster.service;

import java.util.List;
import java.time.LocalDate;
import com.promineotech.blockbuster.entity.Rental;
import com.promineotech.blockbuster.entity.RentalRequest;

public interface CheckOutService {
	
	List<Rental> fetchRentals(int administratorPK, int customerPK);
	Rental createRental(RentalRequest rentalRequest);
	void updateRental(int rentalPK, LocalDate dueDate);
	void deleteRental(int rentalPK);
	
}
