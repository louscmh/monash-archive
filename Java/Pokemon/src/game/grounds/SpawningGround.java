package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import game.items.Pokefruit;
import game.pokemon.Pokemon;

/**
 * SpawningGround abstract class
 * Created by:
 * @author Kerk Han Chin
 */
public abstract class SpawningGround extends Ground {

    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of 'spawning ground' terrain
     */
    public SpawningGround(char displayChar){
        super(displayChar);
    }

    public abstract Pokemon getPokemonToSpawn();

    public abstract Pokefruit getPokefruitToSpawn();

}
