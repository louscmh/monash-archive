package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportActorAction;

/**
 * Door to allow for teleportation to different game maps.
 * Created by:
 * @author Kerk Han Chin
 */
public class Door extends Item {
    /***
     * Constructor.
     * @param destinationName the name of the destination of this door
     * @param destination the Location of the destination of this door
     */
    public Door(String destinationName, Location destination) {
        super("Door", '=', false);
        this.addAction(new TeleportActorAction(destination, destinationName));
    }

}
