package com.promineotech.blockbuster.dao;

import java.util.List;
import com.promineotech.blockbuster.entity.Console;
import com.promineotech.blockbuster.entity.Gameplay;
import com.promineotech.blockbuster.entity.VideoGame;

public interface VideoGameInventoryDao {

	List<VideoGame> fetchVideoGames(Gameplay gameplay, Console console);

}
