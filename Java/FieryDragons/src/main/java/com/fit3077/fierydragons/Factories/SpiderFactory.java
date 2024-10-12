package com.fit3077.fierydragons.Factories;

import com.fit3077.fierydragons.DragonCards.DragonCard;
import com.fit3077.fierydragons.DragonCards.SpiderDragonCard;
import com.fit3077.fierydragons.Game.Player;
import com.fit3077.fierydragons.VolcanoTiles.Caves.Cave;
import com.fit3077.fierydragons.VolcanoTiles.Caves.SpiderCave;
import com.fit3077.fierydragons.VolcanoTiles.SpiderVolcanoTile;
import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;
import javafx.scene.image.Image;

/**
 * A class that represents a Spider Factory that manufactures Spider-related objects
 * as part of the Abstract Factory design pattern.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class SpiderFactory implements VolcanoTileFactory, CaveFactory, DragonCardFactory{

    /**
     * Abstract Factory method that creates a Spider Volcano Tile
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @return A Spider Volcano Tile
     */
    @Override
    public VolcanoTile createVolcanoTile() {
        return new SpiderVolcanoTile();
    }

    /**
     * Abstract Factory method that creates a Spider Cave
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param cavePlayer The player whose dragon token dwells within this cave
     * @return A Spider Cave
     */
    @Override
    public Cave createCave(Player cavePlayer, VolcanoTile caveVolcanoTile) {
        return new SpiderCave(cavePlayer, caveVolcanoTile);
    }

    /**
     * Abstract Factory method that creates a Spider Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param animalCount the number of animals that appear on this Dragon Card that dictate
     *                    how many tiles the Player's dragon token might be moved in a direction.
     * @param displayImage the Image associated with this Dragon Card.
     * @return A Spider Dragon Card
     */
    @Override
    public DragonCard createDragonCard(int animalCount, Image displayImage) {
        return new SpiderDragonCard(animalCount, displayImage);
    }

}
