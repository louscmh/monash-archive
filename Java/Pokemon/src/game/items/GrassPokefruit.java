package game.items;

import game.elements.Element;
import game.status.Status;

/**
 * Grass Pokefruit that allows the player to feed a grass pokefruit and has the grass element
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class GrassPokefruit extends Pokefruit{

    /**
     * Constructor
     */
    public GrassPokefruit(){
        super("Grass Pokefruit", Element.GRASS);
        this.addCapability(Status.CAN_FEED_POKEMON_GRASS_POKEFRUIT);
    }
}
