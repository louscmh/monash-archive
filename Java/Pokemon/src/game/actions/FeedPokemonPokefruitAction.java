package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.affection.AffectionCheck;
import game.elements.Element;
import game.elements.ElementsHelper;
import game.items.Pokefruit;
import game.pokemon.Pokemon;

/**
 * Action class created to feed a nearby pokemon if player has a pokefruit in their inventory
 * Created by:
 * @author Louis Meng Hoe Chow
 */
public class FeedPokemonPokefruitAction extends Action implements AffectionCheck {
    private Pokemon targetPokemon;
    private Pokefruit pokefruit;

    /***
     * constructor
     * @param pokemon
     * @param pokefruit
     */
    public FeedPokemonPokefruitAction(Pokemon pokemon, Item pokefruit) {
        this.targetPokemon = pokemon;
        this.pokefruit = (Pokefruit) pokefruit;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (ElementsHelper.hasAnySimilarElements(pokefruit, targetPokemon.findCapabilitiesByType(Element.class))) { // increases affection if pokemon is same element with the pokefruit, decrease affection otherwise
            String returnString = this.increaseAffection(targetPokemon,20);
            actor.removeItemFromInventory(pokefruit); // remove pokefruit from inventory after use
            return String.format("%s likes it! %s", targetPokemon.getName(), returnString);
        }
        else {
            String returnString = this.decreaseAffection(targetPokemon,10);
            actor.removeItemFromInventory(pokefruit); // remove pokefruit from inventory after use
            return String.format("%s dislikes it! %s", targetPokemon.getName(), returnString);
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return String.format("%s gives a %s to %s(%d AP)",actor,pokefruit,targetPokemon.getName(),this.getAffectionPoint(targetPokemon));
    }
}
