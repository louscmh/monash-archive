package com.fit3077.fierydragons.Factories;

import com.fit3077.fierydragons.Game.Player;
import com.fit3077.fierydragons.VolcanoTiles.Caves.Cave;
import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;

/**
 * An interface for representing a Cave Factory as part of the Abstract Factory design pattern.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public interface CaveFactory {
    /**
     * Abstract Factory method that creates a type of Cave
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param cavePlayer The player whose dragon token dwells within this cave
     * @return A type of Cave
     */
    public Cave createCave(Player cavePlayer, VolcanoTile caveVolcanoTile);
}
