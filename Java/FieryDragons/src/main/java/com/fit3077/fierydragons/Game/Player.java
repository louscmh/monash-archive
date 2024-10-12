package com.fit3077.fierydragons.Game;

import com.fit3077.fierydragons.VolcanoTiles.Caves.Cave;
import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;

/**
 * A class that represents a Player's dragon token within the Fiery Dragons game.
 *
 * Created by:
 * @author Kerk Han Chin
 */

public class Player {
    private int playerId;
    private Cave startCave;
    private VolcanoTile currentPosition;
    private int volcanoTilesToWin;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Cave getStartCave() {
        return startCave;
    }

    public void setStartCave(Cave startCave) {
        this.startCave = startCave;
    }

    public VolcanoTile getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(VolcanoTile currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getVolcanoTilesToWin() {
        return volcanoTilesToWin;
    }

    public void setVolcanoTilesToWin(int volcanoTilesToWin) {
        this.volcanoTilesToWin = volcanoTilesToWin;
    }

    /**
     * Constructor.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param playerId the unique player id that identifies this player
     * @param startCave the Cave that this player's dragon token starts atop and must reach to win the game
     * @param volcanoTilesToWin the number of volcano tiles left that this player must cross to win the game
     */

    public Player(int playerId, Cave startCave, int volcanoTilesToWin) {
        this.playerId = playerId;
        this.startCave = startCave;
        this.currentPosition = startCave.getCaveVolcanoTile();
        this.volcanoTilesToWin = volcanoTilesToWin;
    }
}
