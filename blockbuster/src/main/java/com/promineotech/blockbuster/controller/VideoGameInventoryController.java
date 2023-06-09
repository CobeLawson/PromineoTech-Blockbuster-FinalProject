package com.promineotech.blockbuster.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.blockbuster.entity.Gameplay;
import com.promineotech.blockbuster.entity.Console;
import com.promineotech.blockbuster.entity.VideoGame;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/video_games")
@OpenAPIDefinition(
		info = @Info(title = "Blockbuster Video Game Inventory"), 
		servers = {
				@Server(
						url = "http://localhost:8080", 
						description = "Local server.")})
public interface VideoGameInventoryController {
	// @formatter:off
  @Operation(
      summary = "Returns a list of video games",
      description = "Returns a list of video games in specified genre and media type",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of video games is returned.", 
              content = @Content(mediaType = "application/json", 
                  schema = @Schema(implementation = VideoGame.class))),
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
              name = "gameplay", 
              allowEmptyValue = false,
              required = false,
              description = "The gameplay style of the video game"),
          @Parameter(
              name = "console", 
              allowEmptyValue = false,
              required = false,
              description = "The console version of the video game")
      }
  )
  
  //This is the Post Request and associated method call
  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  List<VideoGame> fetchVideoGames(
      @RequestParam(required = false) 
  		Gameplay gameplay,
      @RequestParam(required = false) 
  		Console console);
  // @formatter:on
}
