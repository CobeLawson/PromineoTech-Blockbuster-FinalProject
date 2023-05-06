package com.promineotech.blockbuster.dao;

import java.util.List;
import com.promineotech.blockbuster.entity.Genre;
import com.promineotech.blockbuster.entity.MediaType;
import com.promineotech.blockbuster.entity.Movie;

public interface MovieInventoryDao {

	List<Movie> fetchMovies(Genre genre, MediaType mediaType);

}
