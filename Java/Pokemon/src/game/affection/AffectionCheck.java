package game.affection;

import edu.monash.fit2099.engine.actors.Actor;
import game.pokemon.Pokemon;

/**
 * An interface that allows classes that implement it to access the AffectionManager instance easily
 * @see AffectionManager
 *
 * Modified by:
 * @author Louis Meng Hoe Chow
 */
public interface AffectionCheck {
    /***
     *
     * @param pokemon
     */
    default void registerPokemon(Pokemon pokemon){
        AffectionManager.getInstance().registerPokemon(pokemon);
    }

    /***
     *
     * @return
     */
    default Actor getTrainer(){
        return AffectionManager.getInstance().getTrainer();
    }

    /***
     *
     * @param pokemon
     * @return
     */
    default int getAffectionPoint(Pokemon pokemon){
        return AffectionManager.getInstance().getAffectionPoint(pokemon);
    }

    /***
     *
     * @param pokemon
     * @param points
     * @return
     */
    default String increaseAffection(Pokemon pokemon, int points){
        return AffectionManager.getInstance().increaseAffection(pokemon, points);
    }

    /***
     *
     * @param pokemon
     * @param points
     * @return
     */
    default String decreaseAffection(Pokemon pokemon, int points){
        return AffectionManager.getInstance().decreaseAffection(pokemon, points);
    }
}
