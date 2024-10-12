package game.actions;

import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Teleports an actor to the specified destination
 * Created by:
 * @author Kerk Han Chin
 */
public class TeleportActorAction extends MoveActorAction {
    /**
     * Constructor to create an Action that will move the Actor to a Location in a given Direction.  A currently-unused
     * menu hotkey will be assigned.
     * <p>
     * Note that this constructor does not check whether the supplied Location is actually in the given direction
     * from the Actor's current location.  This allows for (e.g.) teleporters, etc.
     *
     * @param moveToLocation Location to move to
     * @param direction      String describing the direction to move in, e.g. "north"
     */
    public TeleportActorAction(Location moveToLocation, String direction) {
        super(moveToLocation, direction);
    }

    /**
     * Returns a description of this movement suitable to display in the menu.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player enters pokemon centre"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " enters " + direction;
    }
}
