package game.grounds;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.elements.Element;
import game.elements.ElementsHelper;
import game.time.TimePerception;

import java.util.List;

/**
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Kerk Han Chin
 *
 */
public class Lava extends Ground implements TimePerception {
    private boolean canExpand;
    private boolean canBeDestroyed;

    /**
     * Constructor.
     */
    public Lava() {
        super('^');
        this.addCapability(Element.FIRE);
        this.registerInstance();
    }

    /**
     * During the day, lava can expand
     */
    @Override
    public void dayEffect() {
        canExpand = true;
        canBeDestroyed = false;
    }

    /**
     * During the night, lava can be destroyed
     */
    @Override
    public void nightEffect() {
        canBeDestroyed = true;
        canExpand = false;
    }

    public void solarFlareEffect() {
        this.dayEffect();
    }

    public void newMoonEffect() {
        this.nightEffect();
    }

    /**
     * Lava can also experience the joy of time.
     * @param location The location of the Lava
     */
    @Override
    public void tick(Location location) {
        // During the day, lava ground has a 10% chance to expand (convert its surrounding grounds to Lava)
        if (canExpand && !canBeDestroyed && Math.random() <= 0.1) {
            // Get the lava's surroundings and iterate through the surroundings
            List<Exit> exits = location.getExits();
            for (Exit exit : exits) {
                Location exitLocation = exit.getDestination();
                // The expanding grounds won't convert the Floors, Walls, and grounds with similar elements
                // If the elements aren't the same, then set the surrounding ground to a Lava
                if (!ElementsHelper.hasAnySimilarElements(exitLocation.getGround(), this.findCapabilitiesByType(Element.class))) {
                    exitLocation.setGround(new Lava());
                }
            }
        }
        // During the night, lava has a 10% chance of being destroyed (converted to a Dirt)
        // However, only as long as there's no actor on it
        else if (!canExpand && canBeDestroyed && Math.random() <= 0.1 && !location.containsAnActor()) {
            location.setGround(new Dirt());
        }

    }
}
