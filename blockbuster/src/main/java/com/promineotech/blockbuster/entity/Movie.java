package com.promineotech.blockbuster.entity;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/**
 * @author Cobe Mitchel Lawson
 *
 */
@Data
@Builder
public class Movie {
  private int moviePK;
  private String movieID;
  private String title;
  private BigDecimal price;
  private Genre genre;
  private MediaType mediaType;
}
