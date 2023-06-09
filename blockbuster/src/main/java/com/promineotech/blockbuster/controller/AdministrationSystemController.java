package com.promineotech.blockbuster.controller;

import java.util.List;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.blockbuster.entity.Administrator;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import javax.validation.constraints.Pattern;

@Validated
@RequestMapping("/administration")
@OpenAPIDefinition(
		info = @Info(title = "Blockbuster Administration Service"), 
		servers = {
				@Server(
						url = "http://localhost:8080", 
						description = "Local server.")})
public interface AdministrationSystemController {
	// @formatter:off
  @Operation(
      summary = "Returns a list of administrators",
      description = "Returns a list of administrators in specified role (I.E. Manager, Assistant Manager, Cashier)",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of movies is rented.", 
              content = @Content(mediaType = "application/json", 
                  schema = @Schema(implementation = Administrator.class))),
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
              name = "administratorRole", 
              allowEmptyValue = false,
              required = false,
              description = "The role of the administrator")
      }
  )
  
  //This is the Post Request and associated method call
  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  List<Administrator> fetchAdministrators(
      @Length(max = 50)
      @Pattern(regexp = "[\\w\\s]*")
      @RequestParam(required = false) 
  		String administratorRole);
  // @formatter:on

}
