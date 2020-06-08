package com.example.demo.controllers;

import com.example.demo.models.Game;
import com.example.demo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameRepository repository;

    @GetMapping
    public ResponseEntity<List<Game>> getGames(){
        List<Game> games = repository.findAll();
        if(games.size() > 0){
            return new ResponseEntity<>(games,new HttpHeaders(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ArrayList<>(),new HttpHeaders(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Game> create(){
        Game game = new Game();
        game.setId(UUID.randomUUID());
        game = repository.save(game);
        return new ResponseEntity<>(game, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGame(@PathVariable UUID id, @RequestParam UUID playerId) {
        List<Game> games = repository.findAll();
        for (Game game : games) {
            if(game.id.equals(id)) {
                if(game.getPlayer1() == null)
                    game.setPlayer1(playerId);
                else if (game.getPlayer2() == null){
                    game.setPlayer2(playerId);
                }else if (game.getWinner() == null){
                    game.setWinner(playerId);
                    repository.save(game);
                    return new ResponseEntity<>("You are the winner of this game!", HttpStatus.OK);
                }else
                {
                    return new ResponseEntity<>("The game has maximum number of players", HttpStatus.OK);
                }
                repository.save(game);
                return new ResponseEntity<>("You are now in this game!", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND); //or GONE
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable UUID id) {
        List<Game> games = repository.findAll();
        for (Game game : games) {
            if(game.id.equals(id)) {
                repository.delete(game);
                return new ResponseEntity<>("Game with id = " + game.getId() + " removed", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Game not found", HttpStatus.GONE);
    }

    public Game createGameController(){
        final String uri = "http://localhost:8081/games";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Game> response = restTemplate.exchange(uri, HttpMethod.POST, null, new ParameterizedTypeReference<Game>(){});
        if(response.getStatusCode() == HttpStatus.CREATED)
            return response.getBody();
        else
            return null;
    }

    public boolean joinGameController(UUID gameId, UUID playerId){
        final String uri = "http://localhost:8081/games/" + gameId + "?playerId=" + playerId;
        RestTemplate restTemplate = new RestTemplate();
        Game newGame = new Game();
        restTemplate.put(uri, newGame);
        return true;
    }

    public List<Game> getGamesController(){
        final String uri = "http://localhost:8081/games";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Game>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Game>>(){});
        if(response.getBody() == null)
            System.out.println("Ai dreptate");
        return response.getBody();
    }
}
