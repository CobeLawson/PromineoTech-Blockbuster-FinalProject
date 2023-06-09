package com.promineotech.blockbuster.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

/**
 * @author Cobe Mitchel Lawson
 *
 */
@Data
@Builder

public class Product {
	private int productPK;
	private String productID;
	private String title;
  private String status;
  private LocalDate dueDate;
  private BigDecimal price;
  
}
