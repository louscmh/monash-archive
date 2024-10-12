package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.status.Status;
import game.pokemon.Pokemon;

/**
 * Pokeball that contains a captured Pokemon, and allows the player to summon a Pokemon
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class Pokeball extends Item {

    private Pokemon capturedPokemon;

    /***
     * Constructor.
     * @param capturedPokemon that represents the Pokemon within the Pokeball
     */
    public Pokeball(Pokemon capturedPokemon) {
        super("Pokeball", '0', true);
        setCapturedPokemon(capturedPokemon);
        this.addCapability(Status.CAN_SUMMON_POKEMON);
    }

    public Pokemon getCapturedPokemon() {
        return capturedPokemon;
    }

    public void setCapturedPokemon(Pokemon capturedPokemon) {
        this.capturedPokemon = capturedPokemon;
    }
}
