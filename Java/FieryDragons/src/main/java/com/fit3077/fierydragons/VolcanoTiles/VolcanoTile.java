package com.fit3077.fierydragons.VolcanoTiles;

import com.fit3077.fierydragons.DragonCards.*;
import com.fit3077.fierydragons.Game.Player;

/**
 * An abstract class that represents a Volcano Tile within the Fiery Dragons game
 *
 * Created by:
 * @author Kerk Han Chin
 */
public abstract class VolcanoTile {
    private int id;
    private Player playerOnTop;
    private boolean event = false;

    public Player getPlayerOnTop() {
        return playerOnTop;
    }

    public void setPlayerOnTop(Player playerOnTop) {
        this.playerOnTop = playerOnTop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEvent() {
        return this.event;
    }

    public void setEvent() {
        this.event = !this.event;
    }

    /**
     * Constructor
     *
     * Created by:
     * @author Kerk Han Chin
     *
     */
    public VolcanoTile() {
        this.playerOnTop = null;
    }

    /**
     * This moveWorking handles the case where the last flipped Dragon Card is a Baby Dragon Dragon Card
     * and will return the number of tiles the player's dragon token is due to move
     * based on the animal of the Volcano Tile, with this value being 0 if they don't match,
     * or the number of animals on the dragon card if they do match.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param lastFlippedDragonCard the last flipped dragon card
     * @return the number of tiles the player's dragon token is due to move
     */
    public abstract int moveWorking(BabyDragonDragonCard lastFlippedDragonCard);

    /**
     * This moveWorking handles the case where the last flipped Dragon Card is a Salamander Dragon Card
     * and will return the number of tiles the player's dragon token is due to move
     * based on the animal of the Volcano Tile, with this value being 0 if they don't match,
     * or the number of animals on the dragon card if they do match.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param lastFlippedDragonCard the last flipped dragon card
     * @return the number of tiles the player's dragon token is due to move
     */
    public abstract int moveWorking(SalamanderDragonCard lastFlippedDragonCard);

    /**
     * This moveWorking handles the case where the last flipped Dragon Card is a Bat Dragon Card
     * and will return the number of tiles the player's dragon token is due to move
     * based on the animal of the Volcano Tile, with this value being 0 if they don't match,
     * or the number of animals on the dragon card if they do match.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param lastFlippedDragonCard the last flipped dragon card
     * @return the number of tiles the player's dragon token is due to move
     */
    public abstract int moveWorking(BatDragonCard lastFlippedDragonCard);

    /**
     * This moveWorking handles the case where the last flipped Dragon Card is a Spider Dragon Card
     * and will return the number of tiles the player's dragon token is due to move
     * based on the animal of the Volcano Tile, with this value being 0 if they don't match,
     * or the number of animals on the dragon card if they do match.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param lastFlippedDragonCard the last flipped dragon card
     * @return the number of tiles the player's dragon token is due to move
     */
    public abstract int moveWorking(SpiderDragonCard lastFlippedDragonCard);

    /**
     * This moveWorking handles the case where the last flipped Dragon Card is a Dragon Pirate Dragon Card
     * and will return the number of tiles the player's dragon token is due to move BACKWARDS
     * based on the number of animals on the dragon pirate dragon card.
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param lastFlippedDragonCard the last flipped dragon card
     * @return the number of tiles the player's dragon token is due to move BACKWARDS
     */
    public abstract int moveWorking(DragonPirateDragonCard lastFlippedDragonCard);

    /**
     * This moveWorking handles the case where the last flipped Dragon Card is a Swap Dragon Card
     * and will return the number of tiles the player's dragon token is due to move FORWARDS or BACKWARDS
     * based on the position of the closest dragon token.
     *
     * Created by:
     * @author Louis Meng Hoe Chow
     *
     * @param lastFlippedDragonCard the last flipped dragon card
     * @return the number of tiles the player's dragon token is due to move BACKWARDS
     */
    public abstract int moveWorking(SwapDragonCard lastFlippedDragonCard);
}
