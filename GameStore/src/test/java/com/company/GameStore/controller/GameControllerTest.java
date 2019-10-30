package com.company.GameStore.controller;

import com.company.GameStore.SecurityConfig;
import com.company.GameStore.dto.Game;
import com.company.GameStore.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
@ContextConfiguration
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer service;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private SecurityConfig securityConfig;

    @MockBean
    DataSource dataSource;

    @Before
    public void setUp() throws Exception {
    }

    // ==================================================

    @Test
    public void getGameReturnWithJson() throws Exception {
        Game game = new Game();
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);
        game.setGameId(8);

        String outputJson = mapper.writeValueAsString(game);

        when(service.getGame(8)).thenReturn(game);

        this.mockMvc.perform(get("/game/8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getGameReturn404() throws Exception {
        Game game = new Game();

        int idForGameThatDoesNotExist = 100;

        when(service.getGame(idForGameThatDoesNotExist)).thenThrow(new IllegalArgumentException("Could not find game with matching Id"));

        this.mockMvc.perform(get("/game/" + idForGameThatDoesNotExist))
                .andDo(print())
                .andExpect(status().isNotFound());
    }



    @Test
    @WithMockUser(username = "admin", roles = {"STAFF", "MANAGER", "ADMIN"})
    public void addGame() throws Exception {
        Game inGame = new Game();
        inGame.setTitle("Horizon Zero Dawn");
        inGame.setEsrbRating("T");
        inGame.setDescription("post-apocalypse");
        inGame.setPrice(new BigDecimal("20.65"));
        inGame.setStudio("Guerrilla Games");
        inGame.setQuantity(1);

        String inputJson = mapper.writeValueAsString(inGame);

        Game outGame = new Game();
        outGame.setTitle("Horizon Zero Dawn");
        outGame.setEsrbRating("T");
        outGame.setDescription("post-apocalypse");
        outGame.setPrice(new BigDecimal("20.65"));
        outGame.setStudio("Guerrilla Games");
        outGame.setQuantity(1);
        outGame.setGameId(8);

        String outputJson = mapper.writeValueAsString(outGame);

        when(service.addGame(inGame)).thenReturn(outGame);

        this.mockMvc.perform(post("/game")
                .with(csrf().asHeader())
        .content(inputJson)
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));


    }

    @Test
    public void getAllGames() throws Exception {
        Game game = new Game();
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);
        game.setGameId(8);

        Game game2 = new Game();
        game2.setTitle("Horizon Zero Dawn");
        game2.setEsrbRating("T");
        game2.setDescription("post-apocalypse");
        game2.setPrice(new BigDecimal("20.65"));
        game2.setStudio("Guerrilla Games");
        game2.setQuantity(1);
        game2.setGameId(9);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game);
        gameList.add(game2);

        when(service.getAllGames()).thenReturn(gameList);

        List<Game> listChecker = new ArrayList<>();
        listChecker.addAll(gameList);

        String outputJson = mapper.writeValueAsString(listChecker);

        this.mockMvc.perform(get("/game"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getGamesByStudio() throws Exception {
        Game game = new Game();
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);
        game.setGameId(8);

        Game game2 = new Game();
        game2.setTitle("Horizon Zero Dawn");
        game2.setEsrbRating("T");
        game2.setDescription("post-apocalypse");
        game2.setPrice(new BigDecimal("20.65"));
        game2.setStudio("Guerrilla Games");
        game2.setQuantity(1);
        game2.setGameId(9);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game);
        gameList.add(game2);

        when(service.getGamesByStudio("Guerrilla Games")).thenReturn(gameList);

        List<Game> listChecker = new ArrayList<>();
        listChecker.addAll(gameList);

        String outputJson = mapper.writeValueAsString(listChecker);

        this.mockMvc.perform(get("/game/GetStudio/Guerrilla Games"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getGamesByEsrb() throws Exception {
        Game game = new Game();
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);
        game.setGameId(8);

        Game game2 = new Game();
        game2.setTitle("Horizon Zero Dawn");
        game2.setEsrbRating("T");
        game2.setDescription("post-apocalypse");
        game2.setPrice(new BigDecimal("20.65"));
        game2.setStudio("Guerrilla Games");
        game2.setQuantity(1);
        game2.setGameId(9);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game);
        gameList.add(game2);

        when(service.getGamesByEsrb("T")).thenReturn(gameList);

        List<Game> listChecker = new ArrayList<>();
        listChecker.addAll(gameList);

        String outputJson = mapper.writeValueAsString(listChecker);

        this.mockMvc.perform(get("/game/GetEsrb/T"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getGamesByTitle() throws Exception {
        Game game = new Game();
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);
        game.setGameId(8);

        Game game2 = new Game();
        game2.setTitle("Horizon Zero Dawn");
        game2.setEsrbRating("T");
        game2.setDescription("post-apocalypse");
        game2.setPrice(new BigDecimal("20.65"));
        game2.setStudio("Guerrilla Games");
        game2.setQuantity(1);
        game2.setGameId(9);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game);
        gameList.add(game2);

        when(service.getGamesByTitle("Horizon Zero Dawn")).thenReturn(gameList);

        List<Game> listChecker = new ArrayList<>();
        listChecker.addAll(gameList);

        String outputJson = mapper.writeValueAsString(listChecker);

        this.mockMvc.perform(get("/game/GetTitle/Horizon Zero Dawn"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"STAFF", "MANAGER", "ADMIN"})
    public void updateGame() throws Exception {
        Game game = new Game();
        game.setTitle("Horizon Zero Dawn");
        game.setEsrbRating("T");
        game.setDescription("post-apocalypse");
        game.setPrice(new BigDecimal("20.65"));
        game.setStudio("Guerrilla Games");
        game.setQuantity(1);
        game.setGameId(8);

        String inputJson = mapper.writeValueAsString(game);
        String outputJson = mapper.writeValueAsString(game);

        this.mockMvc.perform(put("/game/8")
                .with(csrf().asHeader())
        .content(inputJson)
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"STAFF", "MANAGER", "ADMIN"})
    public void deleteGame() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/game/8")
                .with(csrf().asHeader()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}