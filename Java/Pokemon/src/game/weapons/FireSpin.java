package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.elements.Element;
import game.items.Fire;

/**
 * Created by:
 * @author Louis Meng Hoe Chow
 */

public class FireSpin extends WeaponItem {
    /**
     * Constructor
     */
    public FireSpin() {
        super("Fire Spin", '-', 70, "fire spins", 90);
        this.addCapability(Element.FIRE);
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        for (Exit exit : currentLocation.getExits()) {
            Location destination = exit.getDestination();
            destination.addItem(new Fire());
        }
    }
}
