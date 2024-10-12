package com.fit3077.fierydragons.Factories;

import com.fit3077.fierydragons.DragonCards.DragonCard;
import javafx.scene.image.Image;

/**
 * An interface for representing a DragonCard Factory as part of the Abstract Factory design pattern.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public interface DragonCardFactory {
    /**
     * Abstract Factory method that creates a type of Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param animalCount the number of animals that appear on this Dragon Card that dictate
     *                    how many tiles the Player's dragon token might be moved in a direction.
     * @param displayImage the Image associated with this Dragon Card.
     * @return A type of Dragon Card
     */
    public DragonCard createDragonCard(int animalCount, Image displayImage);
}
