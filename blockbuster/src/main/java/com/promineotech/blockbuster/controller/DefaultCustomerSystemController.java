package com.promineotech.blockbuster.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.blockbuster.entity.Customer;
import com.promineotech.blockbuster.service.CustomerSystemService;

@RestController
public class DefaultCustomerSystemController implements CustomerSystemController {
	
	@Autowired
	CustomerSystemService customerSystemService;

	@Override
	public List<Customer> fetchCustomers() {
		return customerSystemService.fetchCustomers();
	}

}
