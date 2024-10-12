package com.fit3077.fierydragons.Factories;

import com.fit3077.fierydragons.DragonCards.BabyDragonDragonCard;
import com.fit3077.fierydragons.DragonCards.DragonCard;
import com.fit3077.fierydragons.Game.Player;
import com.fit3077.fierydragons.VolcanoTiles.BabyDragonVolcanoTile;
import com.fit3077.fierydragons.VolcanoTiles.Caves.BabyDragonCave;
import com.fit3077.fierydragons.VolcanoTiles.Caves.Cave;
import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;
import javafx.scene.image.Image;

/**
 * A class that represents a BabyDragon Factory that manufactures BabyDragon-related objects
 * as part of the Abstract Factory design pattern.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class BabyDragonFactory implements VolcanoTileFactory, CaveFactory, DragonCardFactory{

    /**
     * Abstract Factory method that creates a Baby Dragon Volcano Tile
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @return A Baby Dragon Volcano Tile
     */
    @Override
    public VolcanoTile createVolcanoTile() {
        return new BabyDragonVolcanoTile();
    }

    /**
     * Abstract Factory method that creates a Baby Dragon Cave
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param cavePlayer The player whose dragon token dwells within this cave
     * @param caveVolcanoTile The volcano tile associated with this cave
     * @return A Baby Dragon Cave
     */
    @Override
    public Cave createCave(Player cavePlayer, VolcanoTile caveVolcanoTile) {
        return new BabyDragonCave(cavePlayer, caveVolcanoTile);
    }

    /**
     * Abstract Factory method that creates a Baby Dragon Cave
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param animalCount the number of animals that appear on this Dragon Card that dictate
     *                    how many tiles the Player's dragon token might be moved in a direction.
     * @param displayImage the Image associated with this Dragon Card.
     * @return A Baby Dragon Dragon Card
     */
    @Override
    public DragonCard createDragonCard(int animalCount, Image displayImage) {
        return new BabyDragonDragonCard(animalCount, displayImage);
    }
}
