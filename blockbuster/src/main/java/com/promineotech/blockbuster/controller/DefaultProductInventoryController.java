package com.promineotech.blockbuster.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.blockbuster.entity.Product;
import com.promineotech.blockbuster.service.ProductInventoryService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultProductInventoryController implements ProductInventoryController {
	
	@Autowired
	ProductInventoryService productInventoryService;

	@Override
	public List<Product> fetchProducts(String status) {
		log.info("status={}", status);
		return productInventoryService.fetchProducts(status);
	}

}
