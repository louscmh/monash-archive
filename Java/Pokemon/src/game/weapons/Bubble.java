package game.weapons;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.elements.Element;

/**
 * Created by:
 * @author Louis Meng Hoe Chow
 */

public class Bubble extends WeaponItem {
    /**
     * Constructor.
     */
    public Bubble() {
        super("Bubble", '-', 25, "burbles", 80);
        this.addCapability(Element.WATER);
    }
}
