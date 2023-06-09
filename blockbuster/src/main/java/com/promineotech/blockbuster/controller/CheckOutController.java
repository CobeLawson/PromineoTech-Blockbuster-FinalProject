package com.promineotech.blockbuster.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.blockbuster.entity.Rental;
import com.promineotech.blockbuster.entity.RentalRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import javax.validation.Valid;

@Validated
@RequestMapping("/transactions")
@OpenAPIDefinition(
		info = @Info(title = "Blockbuster CheckOut Service"), 
		servers = {
				@Server(
						url = "http://localhost:8080", 
						description = "Local server.")})
public interface CheckOutController {

	// @formatter:off
  @Operation(
      summary = "Create an rental Rental",
      description = "Returns the created Rental",
      responses = {
          @ApiResponse(
              responseCode = "201", 
              description = "The created Rental is returned.", 
              content = @Content(mediaType = "application/json", 
                  schema = @Schema(implementation = Rental.class))),
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
              name = "RentalRequest",
              required = true,
              description = "The rental as JSON")
      }
  )
  
  //This is the Post Request and associated method call
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Rental createRental(
  		@Valid
  		@RequestBody RentalRequest rentalRequest);
  // @formatter:on 

//@formatter:off
 @Operation(
     summary = "Returns a list of Rentals",
     description = "Returns a list of Rentals with specified administrator and customer",
     responses = {
         @ApiResponse(
             responseCode = "200", 
             description = "The list of Rentals is returned.", 
             content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = Rental.class))),
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
             name = "administratorPK", 
             allowEmptyValue = false,
             required = false,
             description = "The administrator who created the rental"),
         @Parameter(
             name = "customerPK", 
             allowEmptyValue = false,
             required = false,
             description = "The customer who checked out the rental")
     }
 )
  
  //This is the Get Request and associated method call
  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  List<Rental> fetchRentals(
      @RequestParam(required = false) 
  		int administratorPK,
      @RequestParam(required = false) 
  		int customerPK
  		);
	// @formatter:on
 
 	//@formatter:off
 	@Operation(
 		summary = "Updates a Rental due date",
    description = "Updates the due date of a Rental transaction",
    responses = {
      @ApiResponse(
      	responseCode = "200", 
        description = "The Rental due date is successfully updated.", 
        content = @Content(mediaType = "application/json", 
        	schema = @Schema(implementation = Rental.class))),
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
      	name = "rentalPK", 
        allowEmptyValue = false,
        required = false,
        description = "The rental PK"),
      @Parameter(
        	name = "dueDate", 
          allowEmptyValue = false,
          required = false,
          description = "The due date of the rental")
    }
  )
 
 //This is the Get Request and associated method call
 @PutMapping
 @ResponseStatus(code = HttpStatus.OK)
 void updateRental(
     @RequestParam(required = false) 
 		int rentalPK,
    @RequestParam(required = false) 
		LocalDate dueDate
 		);
	// @formatter:on

	//@formatter:off
	@Operation(
		summary = "Deletes a Rental",
		description = "The specified rental is deleted from the database",
		responses = {
      @ApiResponse(
      	responseCode = "200", 
        description = "Successfully deleted specified Rental.", 
        content = @Content(mediaType = "application/json", 
        	schema = @Schema(implementation = Rental.class))),
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
        name = "rentalPK", 
        allowEmptyValue = false,
        required = false,
        description = "The PK of the desired rental")
		}
	)

	//This is the Get Request and associated method call
	@DeleteMapping
	@ResponseStatus(code = HttpStatus.OK)
	void deleteRental(
		@RequestParam(required = false) 
			int rentalPK
	);
	// @formatter:on
	
}
