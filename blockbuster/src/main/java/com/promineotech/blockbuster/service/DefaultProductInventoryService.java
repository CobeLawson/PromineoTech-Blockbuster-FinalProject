package com.promineotech.blockbuster.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.blockbuster.dao.ProductInventoryDao;
import com.promineotech.blockbuster.entity.Product;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultProductInventoryService implements ProductInventoryService {

	@Autowired
	private ProductInventoryDao productInventoryDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Product> fetchProducts(String status) {
		log.info("The fetchProducts method was called with status={}", status);
		
		List<Product> products = productInventoryDao.fetchProducts(status);
		
		if(products.isEmpty()) {
			String msg = String.format("No products were found with status=%s", status);
			throw new NoSuchElementException(msg);
		}
		
		return products;
	}

}
