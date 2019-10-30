package com.company.GameStore.dao;

import com.company.GameStore.dto.Game;

import java.util.List;

public interface GameDao {

    Game getGame(int gameId);

    List<Game> getAllGames();

    List<Game> getGamesByStudio(String studio);

    List<Game> getGamesByEsrb(String esrbRating);

    List<Game> getGamesByTitle(String title);

    Game addGame(Game game);

    void updateGame(Game game);

    void deleteGame(int gameId);

}
