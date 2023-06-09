package com.promineotech.blockbuster.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.blockbuster.entity.Genre;
import com.promineotech.blockbuster.entity.MediaType;
import com.promineotech.blockbuster.entity.Movie;
import com.promineotech.blockbuster.service.MovieInventoryService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultMovieInventoryController implements MovieInventoryController {

	@Autowired
	private	MovieInventoryService movieInventoryService;
	
	@Override
	public List<Movie> fetchMovies(Genre genre, MediaType mediaType) {
		log.info("genre={} and mediaType={}", genre, mediaType);
		return movieInventoryService.fetchMovies(genre, mediaType);
	}

}
