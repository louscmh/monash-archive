package game.weapons;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.elements.Element;

/**
 * Created by:
 * @author Louis Meng Hoe Chow
 */

public class Blaze extends WeaponItem{
    /**
     * Constructor
     */
    public Blaze() {
        super("Blaze", '-', 60, "blazes", 90);
        this.addCapability(Element.FIRE);
    }

}
