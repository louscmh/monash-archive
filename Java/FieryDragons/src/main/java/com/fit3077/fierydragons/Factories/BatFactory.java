package com.fit3077.fierydragons.Factories;

import com.fit3077.fierydragons.DragonCards.BatDragonCard;
import com.fit3077.fierydragons.DragonCards.DragonCard;
import com.fit3077.fierydragons.Game.Player;
import com.fit3077.fierydragons.VolcanoTiles.BatVolcanoTile;
import com.fit3077.fierydragons.VolcanoTiles.Caves.BatCave;
import com.fit3077.fierydragons.VolcanoTiles.Caves.Cave;
import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;
import javafx.scene.image.Image;

/**
 * A class that represents a Bat Factory that manufactures Bat-related objects
 * as part of the Abstract Factory design pattern.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class BatFactory implements VolcanoTileFactory, CaveFactory, DragonCardFactory{

    /**
     * Abstract Factory method that creates a Bat Volcano Tile
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @return A Bat Volcano Tile
     */
    @Override
    public VolcanoTile createVolcanoTile() {
        return new BatVolcanoTile();
    }

    /**
     * Abstract Factory method that creates a Bat Cave
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param cavePlayer The player whose dragon token dwells within this cave
     * @return A Bat Cave
     */
    @Override
    public Cave createCave(Player cavePlayer, VolcanoTile caveVolcanoTile) {
        return new BatCave(cavePlayer, caveVolcanoTile);
    }

    /**
     * Abstract Factory method that creates a Bat Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param animalCount the number of animals that appear on this Dragon Card that dictate
     *                    how many tiles the Player's dragon token might be moved in a direction.
     * @param displayImage the Image associated with this Dragon Card.
     * @return A Bat Dragon Card
     */
    @Override
    public DragonCard createDragonCard(int animalCount, Image displayImage) {
        return new BatDragonCard(animalCount, displayImage);
    }

}
