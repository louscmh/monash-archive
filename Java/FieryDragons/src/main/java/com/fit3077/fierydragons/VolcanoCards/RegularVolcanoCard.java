package com.fit3077.fierydragons.VolcanoCards;

import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;

import java.util.List;

/**
 * A class that represents the Regular Volcano Cards within the Fiery Dragons game, composed of Volcano Tiles, without a Cave attached.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class RegularVolcanoCard extends VolcanoCard{
    /**
     * Constructor
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param volcanoTiles a List of Volcano Tiles that compose the Volcano Card.
     */
    public RegularVolcanoCard(List<VolcanoTile> volcanoTiles) {
        super(volcanoTiles);
    }
}
