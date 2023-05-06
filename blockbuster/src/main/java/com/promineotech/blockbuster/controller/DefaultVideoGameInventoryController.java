package com.promineotech.blockbuster.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.blockbuster.entity.Console;
import com.promineotech.blockbuster.entity.Gameplay;
import com.promineotech.blockbuster.entity.VideoGame;
import com.promineotech.blockbuster.service.VideoGameInventoryService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultVideoGameInventoryController implements VideoGameInventoryController {
	
	@Autowired
	VideoGameInventoryService videoGameInventoryService;

	@Override
	public List<VideoGame> fetchVideoGames(Gameplay gameplay, Console console) {
		log.info("gameplay={}, console={}", gameplay, console);
		return videoGameInventoryService.fetchVideoGames(gameplay, console);
	}

}
