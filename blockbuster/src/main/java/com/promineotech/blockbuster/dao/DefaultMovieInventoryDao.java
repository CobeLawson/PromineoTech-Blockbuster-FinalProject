package com.promineotech.blockbuster.dao;

import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.promineotech.blockbuster.entity.Genre;
import com.promineotech.blockbuster.entity.MediaType;
import com.promineotech.blockbuster.entity.Movie;
import lombok.extern.slf4j.Slf4j;

@Service
@Component
@Slf4j
public class DefaultMovieInventoryDao implements MovieInventoryDao {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Movie> fetchMovies(Genre genre, MediaType mediaType) {
		log.info("DAO: genre={}, mediaType={}", genre, mediaType);
		
		// @formatter:off
		String sql = ""
				+ "SELECT * "
				+ "FROM movies "
				+ "WHERE genre = :genre AND media_type = :media_type";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		log.info(genre.name());
		params.put("genre", genre.toString());
		log.info(mediaType.name());
		params.put("media_type", mediaType.toString());
		
		return jdbcTemplate.query(sql, params,
				new RowMapper<>() {
					@Override
					public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						// @formatter:off
						return Movie.builder()
								.moviePK(rs.getInt("movie_pk"))
								.movieID(rs.getString("movie_id"))
								.title(rs.getString("title"))
								.price(rs.getBigDecimal("price"))
								.genre(genre)
								.mediaType(mediaType)
								.build();
						// @formatter:on
					}
		}
				);
	}

}
