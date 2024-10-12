package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.elements.Element;

/**
 * Fire Item that can't be picked up and deals damage to Pokemons standing on it without the FIRE element
 * Created by:
 * @author Louis Meng Hoe Chow
 */
public class Fire extends Item {
    private int turnCounter = 0;

    /***
     * Constructor.
     */
    public Fire() {
        super("Fire", 'v', false);
    }

    /**
     * Tick method to track when to remove the fire
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {

        Actor target = currentLocation.getActor();
        if (target != null && !target.hasCapability(Element.FIRE)) {
            target.hurt(10);
        }

        turnCounter += 1;
        if (turnCounter == 2) {
            currentLocation.removeItem(this);
        }
    }
}
