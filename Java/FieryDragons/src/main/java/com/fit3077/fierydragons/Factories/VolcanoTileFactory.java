package com.fit3077.fierydragons.Factories;

import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;

/**
 * An interface for representing a VolcanoTile Factory as part of the Abstract Factory design pattern.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public interface VolcanoTileFactory {
    /**
     * Abstract Factory method that creates a type of Volcano Tile
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @return A type of Volcano Tile
     */
    public VolcanoTile createVolcanoTile();
}
