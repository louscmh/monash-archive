package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import game.actions.AttackAction;
import game.elements.Element;
import game.elements.ElementsHelper;
import game.pokemon.Pokemon;
import game.weapons.Bubble;
import game.weapons.Ember;
import game.weapons.VineWhip;

/**
 * A class that alters AttackBehavior in a way that allows a pokemon to equip a weapon based on their element type
 * before initiating an AttackAction
 * @see AttackBehaviour
 *
 * Modified by:
 * @author Louis Meng Hoe Chow
 */
public class SpecialAttackBehavior extends AttackBehaviour{

    public Action getAction(Actor actor, GameMap map) {
        Actor target = getRandomDifferentPokemon(actor, map); // method inherited from superclass AttackBehavior
        Pokemon initiatingPokemon = (Pokemon) actor;
        if (target != null) { // condition used to check if there is anyone to attack

            boolean switchSuccess = false;

            if (initiatingPokemon.checkSpecialCondition(map, target)) {
                initiatingPokemon.toggleWeapon(true);
                switchSuccess = true;
            }

            if (switchSuccess) { // used to check if one of the switch cases have been satisfied
                return new AttackAction(target, "here"); // AttackAction with the weapon equipped
            }
            else {
                initiatingPokemon.toggleWeapon(false);
            }
        }
        return null;
    }

    /***
     * A method that compares the relationship between an actor and the ground it is currently standing on based on their element.
     *
     * @param actor
     * @param map
     * @return true if the actor's element can be found in the ground's elements, false otherwise
     */
    public boolean getGroundSynergy(Actor actor, GameMap map) {
        Ground currentGround = map.locationOf(actor).getGround();
        return ElementsHelper.hasAnySimilarElements(actor, currentGround.findCapabilitiesByType(Element.class));
    }
}
