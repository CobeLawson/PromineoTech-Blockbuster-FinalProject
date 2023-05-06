package com.promineotech.blockbuster.dao;

import java.util.List;
import com.promineotech.blockbuster.entity.Customer;

public interface CustomerSystemDao {

	List<Customer> fetchCustomers();

}
