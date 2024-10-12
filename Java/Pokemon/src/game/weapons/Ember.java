package game.weapons;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.elements.Element;

/**
 * Created by:
 * @author Louis Meng Hoe Chow
 */

public class Ember extends WeaponItem {
    /**
     * Constructor
     */
    public Ember() {
        super("Ember", '-', 20, "sparks", 90);
        this.addCapability(Element.FIRE);
    }
}
