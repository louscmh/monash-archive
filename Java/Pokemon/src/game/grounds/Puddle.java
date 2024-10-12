package game.grounds;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.elements.Element;
import game.elements.ElementsHelper;
import game.time.TimePerception;

import java.util.List;

/**
 * Puddle ground can expand or be destroyed depending on the time of day.
 *
 * Created by:
 * @author Kerk Han Chin
 */

public class Puddle extends Ground implements TimePerception {

    private boolean canExpand;
    private boolean canBeDestroyed;

    /**
     * Constructor.
     *
     */
    public Puddle() {
        super('~');
        this.addCapability(Element.WATER);
        this.registerInstance();
    }

    /**
     * During the day, puddle can be destroyed
     */
    @Override
    public void dayEffect() {
        canBeDestroyed = true;
        canExpand = false;
    }

    /**
     * During the night, puddle can expand
     */
    @Override
    public void nightEffect() {
        canExpand = true;
        canBeDestroyed = false;
    }

    public void solarFlareEffect() {
        this.dayEffect();
    }

    public void newMoonEffect() {
        this.nightEffect();
    }

    /**
     * Puddle can also experience the joy of time.
     * @param location The location of the Puddle
     */
    @Override
    public void tick(Location location) {
        // During the night, puddle has a 10% chance to expand (convert its surrounding grounds to Puddle).
        if (canExpand && !canBeDestroyed && Math.random() <= 0.1) {
            // Get the puddle's surroundings and iterate through the surroundings
            List<Exit> exits = location.getExits();
            for (Exit exit : exits) {
                Location exitLocation = exit.getDestination();
                // The expanding grounds won't convert the Floors, Walls, and grounds with similar elements
                // If the elements aren't the same, then set the surrounding ground to a Puddle
                if (!ElementsHelper.hasAnySimilarElements(exitLocation.getGround(), this.findCapabilitiesByType(Element.class))) {
                    exitLocation.setGround(new Puddle());
                }
            }
        }
        // During the day, puddle has a 10% chance of being destroyed (converted to a Dirt).
        else if (!canExpand && canBeDestroyed && Math.random() <= 0.1) {
            location.setGround(new Dirt());
        }
    }
}
