package com.fit3077.fierydragons.DragonCards;

import com.fit3077.fierydragons.Visitors.FlipVisitor;
import com.fit3077.fierydragons.VolcanoTiles.*;
import javafx.scene.image.Image;

/**
 * A class that represents a Salamander Dragon Card in the Fiery Dragons game.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class SalamanderDragonCard extends DragonCard{

    /**
     * Constructor
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param animalCount the number of animals that appear on this Dragon Card that dictate
     *                    how many tiles the Player's dragon token might be moved in a direction.
     * @param displayImage the Image associated with this Dragon Card.
     *
     */
    public SalamanderDragonCard(int animalCount, Image displayImage) {
        super(animalCount, displayImage);
    }

    /**
     * flipAccept takes the base flipVisitor interface as an argument
     * as part of the Visitor Design Pattern.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param flipVisitor the interface declaring a set of visiting methods for flipping a dragon card
     *                    that correspond to each animal variant of a dragon card.
     */
    @Override
    public void flipAccept(FlipVisitor flipVisitor) {
        flipVisitor.flipSalamanderDragonCard(this);
    }

    /**
     * moveDoing takes the VolcanoTile associated with the player's dragon token's current position
     * and passes it to that volcano tile's moveWorking method to identify the animal of the volcano tile
     * via double dispatch.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param playerCurrentPosition the VolcanoTile that the player's dragon token is currently placed on
     * @return the number of tiles the player's dragon token is due to move
     */
    @Override
    public int moveDoing(VolcanoTile playerCurrentPosition) {
        return playerCurrentPosition.moveWorking(this);
    }

    /**
     * This moveChecking handles the case where a salamander dragon card was flipped and the
     * player's dragon token's current position is that of a baby dragon volcano tile (a.k.a. non-matching animals).
     * In this case, it will return 0 as the number of tiles the player's dragon token is due to move.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param playerCurrentPosition the salamander VolcanoTile that the player's dragon token is currently placed on
     * @return the number of tiles the player's dragon token is due to move
     */
    @Override
    public int moveChecking(BabyDragonVolcanoTile playerCurrentPosition) {
        return 0;
    }

    /**
     * This moveChecking handles the case where a salamander dragon card was flipped and the
     * player's dragon token's current position is that of a salamander volcano tile (a.k.a. matching animals).
     * In this case, it will return the number of animals on this dragon card as the number of tiles the player's
     * dragon token is due to move.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param playerCurrentPosition the baby dragon VolcanoTile that the player's dragon token is currently placed on
     * @return the number of tiles the player's dragon token is due to move
     */
    @Override
    public int moveChecking(SalamanderVolcanoTile playerCurrentPosition) {
        return this.getAnimalCount();
    }

    /**
     * This moveChecking handles the case where a salamander dragon card was flipped and the
     * player's dragon token's current position is that of a bat volcano tile (a.k.a. non-matching animals).
     * In this case, it will return 0 as the number of tiles the player's dragon token is due to move.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param playerCurrentPosition the salamander VolcanoTile that the player's dragon token is currently placed on
     * @return the number of tiles the player's dragon token is due to move
     */
    @Override
    public int moveChecking(BatVolcanoTile playerCurrentPosition) {
        return 0;
    }

    /**
     * This moveChecking handles the case where a salamander dragon card was flipped and the
     * player's dragon token's current position is that of a spider volcano tile (a.k.a. non-matching animals).
     * In this case, it will return 0 as the number of tiles the player's dragon token is due to move.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param playerCurrentPosition the salamander VolcanoTile that the player's dragon token is currently placed on
     * @return the number of tiles the player's dragon token is due to move
     */
    @Override
    public int moveChecking(SpiderVolcanoTile playerCurrentPosition) {
        return 0;
    }
}
