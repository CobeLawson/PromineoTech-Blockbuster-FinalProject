package com.promineotech.blockbuster.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.promineotech.blockbuster.entity.Console;
import com.promineotech.blockbuster.entity.Gameplay;
import com.promineotech.blockbuster.entity.VideoGame;
import lombok.extern.slf4j.Slf4j;


@Service
@Component
@Slf4j
public class DefaultVideoGameInventoryDao implements VideoGameInventoryDao {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<VideoGame> fetchVideoGames(Gameplay gameplay, Console console) {
		log.info("DAO: gameplay={}, console={}", gameplay, console);
		
		// @formatter:off
		String sql = ""
				+ "SELECT * "
				+ "FROM video_games "
				+ "WHERE gameplay = :gameplay AND console = :console";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		log.info(gameplay.name());
		params.put("gameplay", gameplay.toString());
		log.info(console.name());
		params.put("console", console.toString());
		
		return jdbcTemplate.query(sql, params,
				new RowMapper<>() {
					@Override
					public VideoGame mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						// @formatter:off
						return VideoGame.builder()
								.videoGamePK(rs.getInt("video_game_pk"))
								.videoGameID(rs.getString("video_game_id"))
								.title(rs.getString("title"))
								.price(rs.getBigDecimal("price"))
								.gameplay(gameplay)
								.console(console)
								.build();
						// @formatter:on
					}
		}
				);
	}

}
