package game.grounds;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.elements.Element;
import game.elements.ElementsHelper;
import game.items.Pokefruit;
import game.items.WaterPokefruit;
import game.pokemon.Pokemon;
import game.pokemon.Squirtle;

import java.util.List;

/**
 * Waterfall Spawning Ground that can spawn Bulbasaurs and Water Pokefruits
 * Created by:
 * @author Kerk Han Chin
 */
public class Waterfall extends SpawningGround {
    /**
     * Constructor.
     *
     */
    public Waterfall() {
        super('W');
        this.addCapability(Element.WATER);
    }

    /**
     * Waterfall can spawn a Squirtle
     * @return Squirtle Pokemon
     */
    @Override
    public Pokemon getPokemonToSpawn() {
        return new Squirtle();
    }

    /**
     * Waterfall can spawn Water Pokefruits
     * @return Water Pokefruit item
     */
    @Override
    public Pokefruit getPokefruitToSpawn() {
        return new WaterPokefruit();
    }

    /**
     * Waterfall can also experience the joy of time.
     * @param location The location of the Waterfall
     */
    public void tick(Location location) {
        // Determine if there are at least 2 Water Element Grounds surrounding the waterfall,
        int numberOfAdjacentWaterGrounds = 0;
        List<Exit> exits = location.getExits();
        for (Exit exit: exits){
            Ground exitGround = exit.getDestination().getGround();
            if (ElementsHelper.hasAnySimilarElements(exitGround, this.findCapabilitiesByType(Element.class))) {
                numberOfAdjacentWaterGrounds++;
            }
        }

        // Waterfall has a 20% chance of spawning a Squirtle, however
        // there must at least two WATER element grounds in its surrounding.

        if (Math.random() <= 0.2 && numberOfAdjacentWaterGrounds >= 2 && !location.containsAnActor()) {
            location.addActor(this.getPokemonToSpawn());
        }

        // Waterfall has a 20% chance of dropping a "Water Pokefruit"

        if (Math.random() <= 0.2) {
            location.addItem(this.getPokefruitToSpawn());
        }
    }
}
