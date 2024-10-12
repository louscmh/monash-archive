package game.grounds;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.elements.Element;
import game.elements.ElementsHelper;
import game.items.Candy;
import game.items.GrassPokefruit;
import game.items.Pokefruit;
import game.pokemon.Bulbasaur;
import game.pokemon.Pokemon;
import game.time.TimePerception;

import java.util.List;

/**
 * Tree Spawning Ground that can spawn Bulbasaurs and drop Grass Pokefruits, and also drop a Candy during the day and
 * expand during the night
 * Created by:
 * @author Kerk Han Chin
 */
public class Tree extends SpawningGround implements TimePerception {

    private boolean canExpand;
    private boolean canDropCandy;

    /**
     * Constructor.
     *
     */
    public Tree() {
        super('T');
        this.addCapability(Element.GRASS);
        this.registerInstance();
    }

    /**
     * Tree can spawn a Bulbasaur
     * @return Bulbasaur Pokemon
     */
    @Override
    public Pokemon getPokemonToSpawn() {
        return new Bulbasaur();
    }

    /**
     * Tree can drop a Grass Pokefruit
     * @return Grass Pokefruit item
     */
    @Override
    public Pokefruit getPokefruitToSpawn() {
        return new GrassPokefruit();
    }

    /**
     * Tree can also experience the joy of time.
     * @param location The location of the Tree
     */
    @Override
    public void tick(Location location) {
        // Determine if the Tree has any surrounding Grass grounds.
        boolean hasGrassGround = false;
        List<Exit> exits = location.getExits();
        for (Exit exit: exits){
            Ground exitGround = exit.getDestination().getGround();
            if (ElementsHelper.hasAnySimilarElements(exitGround, this.findCapabilitiesByType(Element.class))) {
                hasGrassGround = true;
                break;
            }
        }

        // Tree has a 15% chance will spawn a Bulbasaur,
        // AND if there is at least one (1) GRASS element ground in its surrounding
        if (Math.random() <= 0.15 && hasGrassGround && !location.containsAnActor()) {
            location.addActor(this.getPokemonToSpawn());
        }

        // Tree has a 15% chance of dropping a "Grass Pokefruit"
        if (Math.random() <= 0.15) {
            location.addItem(this.getPokefruitToSpawn());
        }

        // During the night, trees have a 10% chance to expand (convert its surroundings to either all Trees or all Hays randomly)
        if (canExpand && !canDropCandy && Math.random() <= 0.1) {
            boolean allTreeExpansion = false;
            boolean allHayExpansion = false;

            // 50% chance of the tree expansion being either
            // an All-Tree Expansion, or an All-Hay Expansion
            if (Math.random() <= 0.5) {
                allTreeExpansion = true;
            }
            else if (Math.random() > 0.5){
                allHayExpansion = true;
            }

            // Loop through the surroundings of the tree and expand
            // which involves either setting every surrounding ground into a Tree or a Hay.
            for (Exit exit : exits) {
                Location exitLocation = exit.getDestination();
                if (!ElementsHelper.hasAnySimilarElements(exitLocation.getGround(), this.findCapabilitiesByType(Element.class))) {
                    if (allTreeExpansion) {
                        exitLocation.setGround(new Tree());
                    }
                    else if (allHayExpansion) {
                        exitLocation.setGround(new Hay());
                    }
                }
            }
        }
        // During the day, Trees have a 5% chance of dropping a Candy.
        else if (!canExpand && canDropCandy && Math.random() <= 0.05) {
            location.addItem(new Candy());
        }

    }

    /**
     * During the day, Tree can drop a Candy
     */
    @Override
    public void dayEffect() {
        canDropCandy = true;
        canExpand = false;
    }

    /**
     * During the night, Tree can expand
     */
    @Override
    public void nightEffect() {
        canExpand = true;
        canDropCandy = false;
    }

    public void solarFlareEffect() {
        this.dayEffect();
    }

    public void newMoonEffect() {
        this.nightEffect();
    }


}
