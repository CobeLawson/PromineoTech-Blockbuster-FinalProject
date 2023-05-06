package com.promineotech.blockbuster.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.blockbuster.dao.VideoGameInventoryDao;
import com.promineotech.blockbuster.entity.Console;
import com.promineotech.blockbuster.entity.Gameplay;
import com.promineotech.blockbuster.entity.VideoGame;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultVideoGameInventoryService implements VideoGameInventoryService {
	
	@Autowired
	VideoGameInventoryDao videoGameInventoryDao;

	@Transactional(readOnly = true)
	@Override
	public List<VideoGame> fetchVideoGames(Gameplay gameplay, Console console) {
		log.info("The fetchVideoGames method was called with gameplay={} and console={}", gameplay, console);
		
		List<VideoGame> videoGames = videoGameInventoryDao.fetchVideoGames(gameplay, console);
		
		if(videoGames.isEmpty()) {
			String msg = String.format("No video games were found with gameplay=%s and console=%s", gameplay, console);
			throw new NoSuchElementException(msg);
		}

		return videoGames;
	}

}
