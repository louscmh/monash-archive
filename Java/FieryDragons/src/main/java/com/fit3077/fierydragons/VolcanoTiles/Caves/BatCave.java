package com.fit3077.fierydragons.VolcanoTiles.Caves;

import com.fit3077.fierydragons.Game.Player;
import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;

/**
 * A class that represents a Bat Cave in the Fiery Dragons game.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class BatCave extends Cave{

    /**
     * Constructor
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param cavePlayer The player whose dragon token dwells within this cave
     * @param caveVolcanoTile The volcano tile associated with this cave
     */
    public BatCave(Player cavePlayer, VolcanoTile caveVolcanoTile) {
        super(cavePlayer, caveVolcanoTile);
    }
}
