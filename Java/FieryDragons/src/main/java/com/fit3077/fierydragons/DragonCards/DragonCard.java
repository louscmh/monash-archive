package com.fit3077.fierydragons.DragonCards;

import com.fit3077.fierydragons.Visitors.FlipVisitor;
import com.fit3077.fierydragons.VolcanoTiles.*;
import javafx.scene.image.Image;

/**
 * A class that represents the flippable Dragon Cards within the Fiery Dragons game
 *
 * Created by:
 * @author Kerk Han Chin
 */
public abstract class DragonCard {
    private boolean flipped;
    private int animalCount;
    private Image displayImage;
    private boolean isSwap;

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public int getAnimalCount() {
        return animalCount;
    }

    public void setAnimalCount(int animalCount) {
        this.animalCount = animalCount;
    }

    public Image getDisplayImage() {
        return displayImage;
    }

    public void setDisplayImage(Image displayImage) {
        this.displayImage = displayImage;
    }

    public boolean isSwap() {
        return isSwap;
    }

    public void setSwap(boolean isSwap) {
        this.isSwap = isSwap;
    }

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
    public DragonCard(int animalCount, Image displayImage) {
        this.flipped = false;
        this.animalCount = animalCount;
        this.displayImage = displayImage;
        this.isSwap = false;
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
    public abstract void flipAccept(FlipVisitor flipVisitor);

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
    public abstract int moveDoing(VolcanoTile playerCurrentPosition);

    /**
     * This moveChecking handles the case where a dragon card was flipped and the
     * player's dragon token's current position is that of a baby dragon volcano tile.
     * Depending on the animal of the dragon card, it will either return 0 or
     * the number of animals on this dragon card as the number of tiles the player's dragon token is due to move.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param playerCurrentPosition the baby dragon VolcanoTile that the player's dragon token is currently placed on
     * @return the number of tiles the player's dragon token is due to move
     */
    public abstract int moveChecking(BabyDragonVolcanoTile playerCurrentPosition);

    /**
     * This moveChecking handles the case where a dragon card was flipped and the
     * player's dragon token's current position is that of a salamander volcano tile.
     * Depending on the animal of the dragon card, it will either return 0 or
     * the number of animals on this dragon card as the number of tiles the player's dragon token is due to move.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param playerCurrentPosition the salamander VolcanoTile that the player's dragon token is currently placed on
     * @return the number of tiles the player's dragon token is due to move
     */
    public abstract int moveChecking(SalamanderVolcanoTile playerCurrentPosition);

    /**
     * This moveChecking handles the case where a dragon card was flipped and the
     * player's dragon token's current position is that of a bat volcano tile.
     * Depending on the animal of the dragon card, it will either return 0 or
     * the number of animals on this dragon card as the number of tiles the player's dragon token is due to move.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param playerCurrentPosition the salamander VolcanoTile that the player's dragon token is currently placed on
     * @return the number of tiles the player's dragon token is due to move
     */
    public abstract int moveChecking(BatVolcanoTile playerCurrentPosition);

    /**
     * This moveChecking handles the case where a dragon card was flipped and the
     * player's dragon token's current position is that of a spider volcano tile.
     * Depending on the animal of the dragon card, it will either return 0 or
     * the number of animals on this dragon card as the number of tiles the player's dragon token is due to move.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param playerCurrentPosition the salamander VolcanoTile that the player's dragon token is currently placed on
     * @return the number of tiles the player's dragon token is due to move
     */
    public abstract int moveChecking(SpiderVolcanoTile playerCurrentPosition);
}
