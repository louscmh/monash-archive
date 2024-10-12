package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.elements.Element;
import game.elements.ElementsHelper;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Louis Meng Hoe Chow
 */
public class AttackBehaviour implements Behaviour {

    private final Random random = new Random();
    /**
     *  HINT: develop a logic to check surrounding, check elements, and return an action to attack that opponent.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Actor targetActor = getRandomDifferentPokemon(actor,map);
        if (targetActor != null) {
            return new AttackAction(targetActor, "here");
        }
        return null; // go to next behaviour
    }

    /**
     * bonus method special for AttackBehavior used to check if there are actors within the surrounding 8 exits and
     * check if they are similar to the user's element, returns the target actor if false.
     * @param actor
     * @param map
     * @return
     */
    public Actor getRandomDifferentPokemon(Actor actor, GameMap map){
        ArrayList<Actor> externalActors = new ArrayList<>();

        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                externalActors.add(destination.getActor());
            }
        }

        while (!externalActors.isEmpty()) {
            int index = random.nextInt(externalActors.size());
            Actor target = externalActors.get(index);
            if(!ElementsHelper.hasAnySimilarElements(actor, target.findCapabilitiesByType(Element.class))){
                return target;
            }
            externalActors.remove(index);
        }
        return null;
    }
}
