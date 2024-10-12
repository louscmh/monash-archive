package game.grounds;

import edu.monash.fit2099.engine.positions.Location;
import game.elements.Element;
import game.items.FirePokefruit;
import game.items.Pokefruit;
import game.pokemon.Charmander;
import game.pokemon.Pokemon;

/**
 * Crater Spawning Ground that can spawn Charmanders and drop Fire Pokefruits
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class Crater extends SpawningGround {
    /**
     * Constructor.
     *
     */
    public Crater() {
        super('O');
        this.addCapability(Element.FIRE);
    }

    /**
     * Crater can spawn a Charmander
     * @return Charmander Pokemon
     */
    @Override
    public Pokemon getPokemonToSpawn() {
        return new Charmander();
    }

    /**
     * Crater can drop a fire Pokefruit
     * @return Fire Pokefruit item
     */
    @Override
    public Pokefruit getPokefruitToSpawn() {
        return new FirePokefruit();
    }

    /**
     * Crater can also experience the joy of time.
     * @param location The location of the Crater
     */
    public void tick(Location location) {
        // Crater has a 10% chance of spawning a Charmander
        if (Math.random() <= 0.1 && !location.containsAnActor()) {
            location.addActor(this.getPokemonToSpawn());
        }

        // Crater has a 25% chance of dropping a "Fire Pokefruit"
        if (Math.random() <= 0.25) {
            location.addItem(this.getPokefruitToSpawn());
        }
    }
}
