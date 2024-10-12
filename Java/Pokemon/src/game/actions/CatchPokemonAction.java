package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.affection.AffectionCheck;
import game.items.Candy;
import game.items.Pokeball;
import game.pokemon.Pokemon;

/***
 * Action class to capture a pokemon if the pokemon has status CATCHABLE
 * Created by:
 * @author Louis Meng Hoe Chow
 */
public class CatchPokemonAction extends Action implements AffectionCheck {
    private Pokemon targetPokemon;
    private String direction;

    /***
     * constructor for the class
     * @param targetPokemon
     * @param direction
     */
    public CatchPokemonAction(Pokemon targetPokemon, String direction) {
        this.targetPokemon = targetPokemon;
        this.direction = direction;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        int pokemonAffection = this.getAffectionPoint(this.targetPokemon);
        if (pokemonAffection >= 50) { // capture the pokemon if this condition is passed, otherwise lower affection point
            actor.addItemToInventory(new Pokeball(targetPokemon));
            map.locationOf(targetPokemon).addItem(new Candy()); // will drop whenever a Pokemon is successfully captured
            map.removeActor(targetPokemon);
            return "You have successfully caught the pokemon!";
        }
        else {
            String returnString = this.decreaseAffection(targetPokemon,10);
            return "You cannot catch this pokemon yet! " + returnString;
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return String.format("%s catches %s at %s(%d AP)", actor, targetPokemon.getName(), direction, this.getAffectionPoint(targetPokemon)); // actor + " catches " + targetPokemon + " at " + direction;
    }
}
