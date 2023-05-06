package com.promineotech.blockbuster.service;

import java.util.List;
import com.promineotech.blockbuster.entity.Product;

public interface ProductInventoryService {

	List<Product> fetchProducts(String status);

}
