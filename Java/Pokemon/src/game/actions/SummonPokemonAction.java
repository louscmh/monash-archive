package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Pokeball;
import game.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Random;

/**
 * Action class created to summon a pokemon next to the player if they have a Pokeball instance in their inventory
 * Created by:
 * @author Louis Meng Hoe Chow
 */
public class SummonPokemonAction extends Action {

    // class variables
    Random random = new Random();
    private Pokeball pokeball;

    /***
     * constructor
     * @param pokeball
     */
    public SummonPokemonAction(Item pokeball) {
        this.pokeball = (Pokeball) pokeball;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        ArrayList<Location> emptyLocations = new ArrayList<>();
        Pokemon summonPokemon = pokeball.getCapturedPokemon();

        // retrieve all empty exits
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (!destination.containsAnActor()) {
                emptyLocations.add(destination);
            }
        }
        // select a random exit to summon the pokemon, fail action otherwise
        if (!emptyLocations.isEmpty()) {
            int index = random.nextInt(emptyLocations.size());
            Location summonLocation = emptyLocations.get(index);
            summonLocation.addActor(summonPokemon);
            actor.removeItemFromInventory(pokeball);
            return String.format("I choose you... %s!", summonPokemon.getName());
        }
        else {
            return "There's no space to summon your pokemon!";
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " summons " + pokeball.getCapturedPokemon().getName();
    }
}
