package com.fit3077.fierydragons.Factories;

import com.fit3077.fierydragons.DragonCards.DragonCard;
import com.fit3077.fierydragons.DragonCards.SalamanderDragonCard;
import com.fit3077.fierydragons.Game.Player;
import com.fit3077.fierydragons.VolcanoTiles.Caves.Cave;
import com.fit3077.fierydragons.VolcanoTiles.Caves.SalamanderCave;
import com.fit3077.fierydragons.VolcanoTiles.SalamanderVolcanoTile;
import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;
import javafx.scene.image.Image;

/**
 * A class that represents a Salamander Factory that manufactures Salamander-related objects
 * as part of the Abstract Factory design pattern.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class SalamanderFactory implements VolcanoTileFactory, CaveFactory, DragonCardFactory{

    /**
     * Abstract Factory method that creates a Salamander Volcano Tile
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @return A Salamander Volcano Tile
     */
    @Override
    public VolcanoTile createVolcanoTile() {
        return new SalamanderVolcanoTile();
    }

    /**
     * Abstract Factory method that creates a Salamander Cave
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param cavePlayer The player whose dragon token dwells within this cave
     * @return A Salamander Cave
     */
    @Override
    public Cave createCave(Player cavePlayer, VolcanoTile caveVolcanoTile) {
        return new SalamanderCave(cavePlayer, caveVolcanoTile);
    }

    /**
     * Abstract Factory method that creates a Salamander Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param animalCount the number of animals that appear on this Dragon Card that dictate
     *                    how many tiles the Player's dragon token might be moved in a direction.
     * @param displayImage the Image associated with this Dragon Card.
     * @return A Salamander Dragon Card
     */
    @Override
    public DragonCard createDragonCard(int animalCount, Image displayImage) {
        return new SalamanderDragonCard(animalCount, displayImage);
    }

}
