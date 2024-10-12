package com.fit3077.fierydragons.Game;

import com.fit3077.fierydragons.DragonCards.BabyDragonDragonCard;
import com.fit3077.fierydragons.DragonCards.DragonCard;
import com.fit3077.fierydragons.Factories.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A class that represents the main operation of the Fiery Dragons game
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class Game {
    private Volcano volcano;
    private List<Player> players;
    private Player currentPlayer;
    private int turn;

    public Volcano getVolcano() {
        return volcano;
    }

    public void setVolcano(Volcano volcano) {
        this.volcano = volcano;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * Constructor
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param volcano the Volcano that comprises the board of the Fiery Dragons game
     * @param players the list of Players that will be playing the Fiery Dragons game
     */
    public Game(Volcano volcano, List<Player> players) {
        this.volcano = volcano;
        this.players = players;
        this.currentPlayer = players.get(0);
        this.turn = 0;

    }
}
