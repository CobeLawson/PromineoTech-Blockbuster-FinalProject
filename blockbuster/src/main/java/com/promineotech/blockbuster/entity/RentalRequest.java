package com.promineotech.blockbuster.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class RentalRequest {
	@NotNull
	@Length(max = 50)
	@Pattern(regexp = "[\\w\\s]*")
	private String administrator;
	
	@NotNull
	@Length(max = 50)
	@Pattern(regexp = "[\\w\\s]*")
	private String customer;

	@NotNull
  private LocalDate date;
  
	@NotNull
  private LocalDate dueDate;
	
	@Positive
	@Min(1)
	@Max(1000)
	private BigDecimal price;

	private List<
		@Length(max = 30) 
		@Pattern(regexp = "[\\w\\s]*") 
  	String> productList;
}
