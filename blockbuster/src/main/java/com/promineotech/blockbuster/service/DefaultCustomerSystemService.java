package com.promineotech.blockbuster.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.blockbuster.dao.CustomerSystemDao;
import com.promineotech.blockbuster.entity.Customer;

@Service
public class DefaultCustomerSystemService implements CustomerSystemService {
	
	@Autowired
	CustomerSystemDao customerSystemDao;

	@Transactional(readOnly = true)
	@Override
	public List<Customer> fetchCustomers() {
		
		List<Customer> customers = customerSystemDao.fetchCustomers();
		
		return customers;
	}

}
