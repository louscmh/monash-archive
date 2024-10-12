package com.fit3077.fierydragons.VolcanoTiles.Caves;

import com.fit3077.fierydragons.Game.Player;
import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;

/**
 * A class that represents the Caves in Fiery Dragons where the Player dragon tokens dwell
 *
 * Created by:
 * @author Kerk Han Chin
 */
public abstract class Cave{
    private Player cavePlayer;
    private VolcanoTile caveVolcanoTile;

    public Player getCavePlayer() {
        return cavePlayer;
    }

    public void setCavePlayer(Player cavePlayer) {
        this.cavePlayer = cavePlayer;
    }

    public VolcanoTile getCaveVolcanoTile() {
        return caveVolcanoTile;
    }

    public void setCaveVolcanoTile(VolcanoTile caveVolcanoTile) {
        this.caveVolcanoTile = caveVolcanoTile;
    }

    /**
     * Constructor
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param cavePlayer The player whose dragon token dwells within this cave
     * @param caveVolcanoTile The volcano tile associated with this cave
     */
    public Cave(Player cavePlayer, VolcanoTile caveVolcanoTile) {
        this.cavePlayer = cavePlayer;
        this.caveVolcanoTile = caveVolcanoTile;
    }
}
