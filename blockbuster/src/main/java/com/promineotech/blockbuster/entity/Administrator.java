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
public class Administrator {
  private int administratorPK;
  private String administratorRole;
  private String administratorName;
  private String administratorPhone;
  
}
