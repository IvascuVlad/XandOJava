package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;
    public boolean itsOver = false;
    UUID player1 = null;
    UUID player2 = null;
    UUID winner;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isItsOver() {
        return itsOver;
    }

    public void setItsOver(boolean itsOver) {
        this.itsOver = itsOver;
    }

    public UUID getPlayer1() {
        return player1;
    }

    public void setPlayer1(UUID player1) {
        this.player1 = player1;
    }

    public UUID getPlayer2() {
        return player2;
    }

    public void setPlayer2(UUID player2) {
        this.player2 = player2;
    }

    public UUID getWinner() {
        return winner;
    }

    public void setWinner(UUID winner) {
        this.winner = winner;
    }
}
