package com.company.GameStore.controller;

import com.company.GameStore.dto.Game;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping( value = "/game")
public class GameController {

    @Autowired
    ServiceLayer service;
//    GameDao gameDao;

    // =========== ADD GAME ===========

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Game addGame(@RequestBody @Valid Game game) {
        return service.addGame(game);
    }

    // =========== GET ALL GAMES ===========

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Game> getAllGames() {
        if (service.getAllGames().size() > 0) {
            return service.getAllGames();
        } else {
            throw new IllegalArgumentException("No games found");
        }
    }
    // =========== GET GAME BY STUDIO ===========

    @RequestMapping(value = "/GetStudio/{studio}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Game> getGamesByStudio(@PathVariable String studio) {
        if (service.getGamesByStudio(studio).size() > 0) {
            return service.getGamesByStudio(studio);
        } else {
            throw new IllegalArgumentException("No games by that studio found");
        }
    }

    // =========== GET GAME BY ESRB ===========

    @RequestMapping(value = "/GetEsrb/{esrbRating}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Game> getGamesByEsrb(@PathVariable String esrbRating) {
        if (service.getGamesByEsrb(esrbRating).size() > 0) {
            return service.getGamesByEsrb(esrbRating);
        } else {
            throw new IllegalArgumentException("No games with that Esrb Rating");
        }
    }

    // =========== GET GAME BY TITLE ===========

    @RequestMapping(value = "/GetTitle/{title}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Game> getGamesByTitle(@PathVariable String title) {
        if (service.getGamesByTitle(title).size() > 0) {
            return service.getGamesByTitle(title);
        } else {
            throw new IllegalArgumentException("No games with that title found");
        }
    }


    // =========== GET GAME ===========

    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Game getGame(@PathVariable int gameId) {
//        if (service.getAllGames().contains(service.getGame(gameId))) {
            return service.getGame(gameId);
//        } else {
//            throw new IllegalArgumentException("No games with matching ID");
//        }
    }

    // =========== UPDATE GAME ===========

    @RequestMapping(value = "/{gameId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateGame(@RequestBody @Valid Game game, @PathVariable int gameId) {
//        if (service.getAllGames().contains(service.getGame(gameId))) {
            game.setGameId(gameId);
            service.updateGame(game);
//        } else {
//            throw new IllegalArgumentException("Can't update, no game with a matching ID");
//        }
    }

    // =========== DELETE GAME ===========

    @RequestMapping( value = "/{gameId}", method = RequestMethod.DELETE)
    @ResponseStatus( value = HttpStatus.OK)
    public void deleteGame(@PathVariable int gameId) {
//        if (service.getAllGames().contains(service.getGame(gameId))) {
            service.deleteGame(gameId);
//        } else {
//            throw new IllegalArgumentException("No matching ID, nothing to delete");
//        }
    }


}
