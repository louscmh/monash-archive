package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.elements.Element;

/**
 * Pokefruit abstract class that ensures Pokefruit display char is 'f' and that Pokefruits are always portable.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public abstract class Pokefruit extends Item {
    /***
     * Constructor.
     *  @param name the name of this Pokefruit
     *  @param element the element of this Pokefruit
     *
     */
    public Pokefruit(String name, Element element) {
        super(name, 'f', true);
        this.addCapability(element);
    }
}
