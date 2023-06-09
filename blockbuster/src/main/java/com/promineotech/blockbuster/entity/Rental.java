package com.promineotech.blockbuster.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * @author Cobe Mitchel Lawson
 *
 */
@Data
@Builder
public class Rental {
  private int rentalPK;
  private Administrator administrator;
  private Customer customer;
  private LocalDate date;
  private LocalDate dueDate;
  private BigDecimal price;
  private List<Product> productList;
  
}
