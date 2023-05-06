package com.promineotech.blockbuster.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.blockbuster.dao.MovieInventoryDao;
import com.promineotech.blockbuster.entity.Genre;
import com.promineotech.blockbuster.entity.MediaType;
import com.promineotech.blockbuster.entity.Movie;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultMovieInventoryService implements MovieInventoryService {

	@Autowired
	private MovieInventoryDao movieInventoryDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Movie> fetchMovies(Genre genre, MediaType mediaType) {
		log.info("The fetchMovies method was called with genre={} and mediaType={}", genre, mediaType);
		
		List<Movie> movies = movieInventoryDao.fetchMovies(genre, mediaType);
		
		if(movies.isEmpty()) {
			String msg = String.format("No movies were found with genre=%s and mediaType=%s", genre, mediaType);
			throw new NoSuchElementException(msg);
		}
		
		return movies;
	}

}
