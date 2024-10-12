package com.fit3077.fierydragons.VolcanoCards;

import com.fit3077.fierydragons.VolcanoTiles.Caves.Cave;
import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;

import java.util.List;

/**
 * A class that represents the Cave Volcano Cards within the Fiery Dragons game, composed of Volcano Tiles, with a Cave attached.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class CaveVolcanoCard extends VolcanoCard{
    private Cave cave;

    public Cave getCave() {
        return cave;
    }

    public void setCave(Cave cave) {
        this.cave = cave;
    }

    /**
     * Constructor
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param volcanoTiles a List of Volcano Tiles that compose the Volcano Card.
     * @param cave the Cave that's attached to this Volcano Card.
     */
    public CaveVolcanoCard(List<VolcanoTile> volcanoTiles, Cave cave) {
        super(volcanoTiles);
        this.cave = cave;
    }
}
