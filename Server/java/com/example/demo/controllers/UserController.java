package com.example.demo.controllers;


import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping
    public ResponseEntity<String> getGames(@RequestParam String username, @RequestParam String password) {
        List<User> users = repository.findAll();
        for (User user : users) {
            if(user.getUsername().equals(username))
                if(user.getPassword().equals(password)){
                    return new ResponseEntity<>("Logged in", HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>("Wrong password", HttpStatus.OK);
                }
        }
        return new ResponseEntity<>("Not found", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestParam String username, @RequestParam String password){
        List<User> users = repository.findAll();
        for (User user : users) {
            if(user.getUsername().equals(username)){
                return new ResponseEntity<User>(null, new HttpHeaders(), HttpStatus.OK);
            }
        }
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(username);
        user.setPassword(password);
        user = repository.save(user);
        return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateGame(@RequestParam String username, @RequestParam String password) {
        return new ResponseEntity<>("Game not found", HttpStatus.FOUND); //or GONE
    }

    @DeleteMapping
    public ResponseEntity<String> deleteGame(@RequestParam String username) {
        List<User> users = repository.findAll();
        for (User user : users) {
            if(user.getUsername().equals(username)) {
                repository.delete(user);
                return new ResponseEntity<>("User with username = " + username + " removed", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Not found", HttpStatus.OK);
    }
    public User createPlayerController(String username,String password) {
        final String uri = "http://localhost:8081/user";
        String urio = "http://localhost:8081/user?username="+username+"&password="+password;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();

        ResponseEntity<User> response = restTemplate.postForEntity(urio, map, User.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } if(response.getStatusCode() == HttpStatus.OK){
            return new User();
        }else {
            return null;
        }
    }
}
