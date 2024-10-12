package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.pokemon.Pokemon;

/**
 * Created by:
 * @author Louis Meng Hoe Chow
 */
public class EvolveAction extends Action {

    private Pokemon targetPokemon;

    public EvolveAction(Pokemon targetPokemon) {
        this.targetPokemon = targetPokemon;
    }

    @Override
    public String execute(Actor actor, GameMap map) {

        Pokemon evolvedPokemon = targetPokemon.getEvolvedVariant();
        Location evolutionLocation = map.locationOf(targetPokemon);

        map.removeActor(targetPokemon);
        evolutionLocation.addActor(evolvedPokemon);

        return String.format("%s has evolved to %s!", targetPokemon.getName(), evolvedPokemon.getName());
    }

    @Override
    public String menuDescription(Actor actor) {
        return String.format("Evolve %s to the next stage", targetPokemon);
    }
}
