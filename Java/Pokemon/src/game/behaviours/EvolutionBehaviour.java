package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.EvolveAction;
import game.pokemon.Pokemon;
import game.status.Status;

/**
 * Created by:
 * @author Louis Meng Hoe Chow
 */
public class EvolutionBehaviour implements Behaviour {

    private int turnCounter;

    public EvolutionBehaviour(){
        turnCounter = 0;
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        turnCounter += 1;
        if (turnCounter >= 20 && checkCondition(actor, map)) {
            return new EvolveAction((Pokemon) actor);
        }
        return null;
    }

    public boolean checkCondition(Actor actor, GameMap map) {

        if (!actor.hasCapability(Status.CAN_EVOLVE)) {
            return false;
        }

        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                return false;
            }
        }
        return true;
    }
}
