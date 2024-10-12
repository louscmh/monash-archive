package com.fit3077.fierydragons.VolcanoCards;

import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;

import java.util.List;

/**
 * An abstract class that represents the Volcano Cards within the Fiery Dragons game, composed of Volcano Tiles.
 *
 * Created by:
 * @author Kerk Han Chin
 */

public abstract class VolcanoCard {
    private List<VolcanoTile> volcanoTiles;

    public List<VolcanoTile> getVolcanoTiles() {
        return volcanoTiles;
    }

    public void setVolcanoTiles(List<VolcanoTile> volcanoTiles) {
        this.volcanoTiles = volcanoTiles;
    }

    /**
     * Constructor
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param volcanoTiles a List of Volcano Tiles that compose the Volcano Card.
     */
    public VolcanoCard(List<VolcanoTile> volcanoTiles) {
        this.volcanoTiles = volcanoTiles;
    }
}
