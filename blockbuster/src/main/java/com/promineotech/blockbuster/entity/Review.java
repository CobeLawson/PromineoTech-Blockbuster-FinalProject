package com.promineotech.blockbuster.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Cobe Mitchel Lawson
 *
 */
@Data
@Builder
public class Review {
  private int reviewPK;
  private String reviewString;
  private Product product;
  private Customer customer;
}
