package com.fit3077.fierydragons.VolcanoTiles;

import com.fit3077.fierydragons.DragonCards.*;

/**
 * A class that represents a Bat Volcano Tile in the Fiery Dragons game.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class BatVolcanoTile extends VolcanoTile{
    /**
     * Constructor
     *
     * Created by:
     * @author Kerk Han Chin
     *
     */
    public BatVolcanoTile() {
        super();
    }

    /**
     * As per Double Dispatch, at this point, it has been identified that the animal of the volcano tile that the player's dragon token
     * is currently atop to be a Bat Volcano Tile.
     * This moveWorking handles the case where the last flipped Dragon Card is a Baby Dragon Dragon Card
     * and will return the number of tiles the player's dragon token is due to move
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param lastFlippedDragonCard the last flipped dragon card
     * @return the number of tiles the player's dragon token is due to move
     */
    @Override
    public int moveWorking(BabyDragonDragonCard lastFlippedDragonCard) {
        return lastFlippedDragonCard.moveChecking(this);
    }

    /**
     * As per Double Dispatch, at this point, it has been identified that the animal of the volcano tile that the player's dragon token
     * is currently atop to be a Bat Volcano Tile.
     * This moveWorking handles the case where the last flipped Dragon Card is a Salamander Dragon Card
     * and will return the number of tiles the player's dragon token is due to move
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param lastFlippedDragonCard the last flipped dragon card
     * @return the number of tiles the player's dragon token is due to move
     */
    @Override
    public int moveWorking(SalamanderDragonCard lastFlippedDragonCard) {
        return lastFlippedDragonCard.moveChecking(this);
    }

    /**
     * As per Double Dispatch, at this point, it has been identified that the animal of the volcano tile that the player's dragon token
     * is currently atop to be a Bat Volcano Tile.
     * This moveWorking handles the case where the last flipped Dragon Card is a Bat Dragon Card
     * and will return the number of tiles the player's dragon token is due to move
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param lastFlippedDragonCard the last flipped dragon card
     * @return the number of tiles the player's dragon token is due to move
     */
    @Override
    public int moveWorking(BatDragonCard lastFlippedDragonCard) {
        return lastFlippedDragonCard.moveChecking(this);
    }

    /**
     * As per Double Dispatch, at this point, it has been identified that the animal of the volcano tile that the player's dragon token
     * is currently atop to be a Bat Volcano Tile.
     * This moveWorking handles the case where the last flipped Dragon Card is a Spider Dragon Card
     * and will return the number of tiles the player's dragon token is due to move
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param lastFlippedDragonCard the last flipped dragon card
     * @return the number of tiles the player's dragon token is due to move
     */
    @Override
    public int moveWorking(SpiderDragonCard lastFlippedDragonCard) {
        return lastFlippedDragonCard.moveChecking(this);
    }

    /**
     * As per Double Dispatch, at this point, it has been identified that the animal of the volcano tile that the player's dragon token
     * is currently atop to be a Bat Volcano Tile.
     * This moveWorking handles the case where the last flipped Dragon Card is a Dragon Pirate Dragon Card
     * and will return the number of tiles the player's dragon token is due to move BACKWARDS
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param lastFlippedDragonCard the last flipped dragon card
     * @return the number of tiles the player's dragon token is due to move
     */
    @Override
    public int moveWorking(DragonPirateDragonCard lastFlippedDragonCard) {
        return lastFlippedDragonCard.moveChecking(this);
    }

    /**
     * As per Double Dispatch, at this point, it has been identified that the animal of the volcano tile that the player's dragon token
     * is currently atop to be a Spider Volcano Tile.
     * This moveWorking handles the case where the last flipped Dragon Card is a Dragon Pirate Dragon Card
     * and will return the number of tiles the player's dragon token is due to move FORWARDS or BACKWARDS
     *
     * @param lastFlippedDragonCard the last flipped dragon card
     * @return the number of tiles the player's dragon token is due to move
     */
    @Override
    public int moveWorking(SwapDragonCard lastFlippedDragonCard) {
        return lastFlippedDragonCard.moveChecking(this);
    }
}
