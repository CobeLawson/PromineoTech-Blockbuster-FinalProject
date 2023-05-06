package com.promineotech.blockbuster.dao;

import java.util.List;
import com.promineotech.blockbuster.entity.Product;

public interface ProductInventoryDao {

	List<Product> fetchProducts(String status);

}
