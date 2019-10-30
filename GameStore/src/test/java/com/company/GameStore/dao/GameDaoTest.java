package com.company.GameStore.dao;

import com.company.GameStore.dto.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameDaoTest {

    @Autowired
    private GameDao gameDao;

    private Game game1, game2;

    @Before
    public void setUp() throws Exception {
        List<Game> games = gameDao.getAllGames();
        for (Game g: games) {
            gameDao.deleteGame(g.getGameId());
        }

        game1 = new Game();
        game1.setTitle("Horizon Zero Dawn");
        game1.setEsrbRating("T");
        game1.setDescription("KickButt young woman, robots, post-apocalypse.");
        game1.setPrice(new BigDecimal("45.99"));
        game1.setStudio("Guerrilla Games");
        game1.setQuantity(25);

        game2 = new Game();
        game2.setTitle("Fallout 4");
        game2.setEsrbRating("T");
        game2.setDescription("post-apocalypse choose your on adventure.");
        game2.setPrice(new BigDecimal("30.00"));
        game2.setStudio("Bethesda");
        game2.setQuantity(50);

    }

    @Test
    public void addGetDeleteGame() {
        game1 = gameDao.addGame(game1);

        Game gameTest = gameDao.getGame(game1.getGameId());

        assertEquals(gameTest, game1);

        gameDao.deleteGame(game1.getGameId());

        gameTest = gameDao.getGame(game1.getGameId());

        assertNull(gameTest);
    }

    @Test
    public void getAllGames() {
        game1 = gameDao.addGame(game1);
        game2 = gameDao.addGame(game2);

        List<Game> gameList = gameDao.getAllGames();

        assertEquals(2, gameList.size());
    }

    @Test
    public void getGamesByStudio() {
        game1 = gameDao.addGame(game1);
        game2 = gameDao.addGame(game2);

        List<Game> gameList = gameDao.getGamesByStudio("Bethesda");

        assertEquals(1, gameList.size());
    }

    @Test
    public void getGamesByEsrb() {
        game1 = gameDao.addGame(game1);
        game2 = gameDao.addGame(game2);

        List<Game> gameList = gameDao.getGamesByEsrb("T");

        assertEquals(2, gameList.size());
    }

    @Test
    public void getGamesByTitle() {
        game1 = gameDao.addGame(game1);
        game2 = gameDao.addGame(game2);

        List<Game> gameList = gameDao.getGamesByTitle("Horizon Zero Dawn");

        assertEquals(1, gameList.size());
    }

    @Test
    public void updateGame() {
        game1 = gameDao.addGame(game1);
        game1.setStudio("Gorilla Games");
        gameDao.updateGame(game1);

        Game gameTest = gameDao.getGame(game1.getGameId());

        assertEquals(gameTest, game1);
    }
}