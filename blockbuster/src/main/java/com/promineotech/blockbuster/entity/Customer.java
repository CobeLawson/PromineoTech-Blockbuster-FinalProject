/**
 * 
 */
package com.promineotech.blockbuster.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Cobe Mitchel Lawson
 *
 */
@Data
@Builder
public class Customer {
  private int customerPK;
  private String customerName;
  private String customerPhone;
  private int customerAge;
  
}
