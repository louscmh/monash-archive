package game.items;

import game.elements.Element;
import game.status.Status;

/**
 * Water Pokefruit that allows the player to feed a water pokefruit and has the water element
 * Created by:
 * @author Kerk Han Chin
 */
public class WaterPokefruit extends Pokefruit{

    /**
     * Constructor
     *
     */
    public WaterPokefruit(){
        super("Water Pokefruit", Element.WATER);
        this.addCapability(Status.CAN_FEED_POKEMON_WATER_POKEFRUIT);
    }
}

