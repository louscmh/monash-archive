package game.items;

import game.elements.Element;
import game.status.Status;

/**
 * Fire Pokefruit that allows the player to feed a fire pokefruit and has the fire element
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class FirePokefruit extends Pokefruit{

    /**
     * Constructor
     */
    public FirePokefruit(){
        super("Fire Pokefruit", Element.FIRE);
        this.addCapability(Status.CAN_FEED_POKEMON_FIRE_POKEFRUIT);
    }
}
