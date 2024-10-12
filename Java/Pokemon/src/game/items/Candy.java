package game.items;

import edu.monash.fit2099.engine.items.Item;

/**
 * Candy item that is dropped by a tree and whenever a Pokemon is successfully captured.
 * Created by:
 * @author Kerk Han Chin
 */
public class Candy extends Item {
    /***
     * Constructor.
     */
    public Candy() {
        super("Candy", '*', true);
    }
}
