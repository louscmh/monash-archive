package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import game.elements.Element;

/**
 * Hay ground that is of the grass element
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class Hay extends Ground {

    /**
     * Constructor.
     *
     */
    public Hay() {
        super(',');
        this.addCapability(Element.GRASS);
    }
}
