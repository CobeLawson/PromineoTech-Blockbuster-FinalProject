package com.promineotech.blockbuster.controller;

import java.util.List;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.blockbuster.entity.Product;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/products")
@OpenAPIDefinition(
		info = @Info(title = "Blockbuster Product Inventory"), 
		servers = {
				@Server(
						url = "http://localhost:8080", 
						description = "Local server.")})
public interface ProductInventoryController {
	// @formatter:off
  @Operation(
      summary = "Returns a list of products",
      description = "Returns a list of products with specified status (I.E. AVAILABLE or UNAVAILABLE)",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of products is returned.", 
              content = @Content(mediaType = "application/json", 
                  schema = @Schema(implementation = Product.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request paramaters are invalid.", 
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404", 
              description = "A product was not found with the input criteria.", 
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500", 
              description = "An unplanned error occurred.", 
              content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(
              name = "status", 
              allowEmptyValue = false,
              required = false,
              description = "The status of the product")
      }
  )
  
  //This is the Post Request and associated method call
  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  List<Product> fetchProducts(
      @Length(max = 50)
      @Pattern(regexp = "[\\w\\s]*")
      @RequestParam(required = false) 
  		String status);
  // @formatter:on

}
