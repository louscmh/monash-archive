package com.fit3077.fierydragons.Factories;

import com.fit3077.fierydragons.DragonCards.DragonCard;
import com.fit3077.fierydragons.DragonCards.DragonPirateDragonCard;
import com.fit3077.fierydragons.DragonCards.SwapDragonCard;
import javafx.scene.image.Image;

/**
 * A class that represents a DragonPirate Factory that manufactures DragonPirate-related objects
 * as part of the Abstract Factory design pattern.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class SwapCardFactory implements DragonCardFactory{

    /**
     * Abstract Factory method that creates a Dragon Pirate Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param animalCount the number of animals that appear on this Dragon Card that dictate
     *                    how many tiles the Player's dragon token might be moved in a direction.
     * @param displayImage the Image associated with this Dragon Card.
     * @return A Dragon Pirate Dragon Card
     */
    @Override
    public DragonCard createDragonCard(int animalCount, Image displayImage) {
        return new SwapDragonCard(animalCount, displayImage);
    }
}
