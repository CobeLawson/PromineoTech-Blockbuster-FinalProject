package com.promineotech.blockbuster.service;

import java.util.List;
import com.promineotech.blockbuster.entity.Genre;
import com.promineotech.blockbuster.entity.MediaType;
import com.promineotech.blockbuster.entity.Movie;

public interface MovieInventoryService {

	List<Movie> fetchMovies(Genre genre, MediaType mediaType);

}
