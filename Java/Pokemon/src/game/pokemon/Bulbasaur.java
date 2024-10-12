package game.pokemon;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.elements.Element;
import game.elements.ElementsHelper;
import game.status.Status;
import game.weapons.Ember;
import game.weapons.VineWhip;
import game.behaviours.Behaviour;
import game.time.TimePerception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by:
 * @author Kerk Han Chin
 * Modified by:
 * @author Louis Meng Hoe Chow
 */
public class Bulbasaur extends Pokemon implements TimePerception {


    private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour

    /**
     * Constructor.
     *
     */
    public Bulbasaur() {
        super("Bulbasaur", 'b', 100);
        this.addCapability(Element.GRASS);
        this.addCapability(Status.CATCHABLE);
        this.registerInstance();
        this.addCapability(Status.CAN_MOVE);
        this.addSpecialAttack(new VineWhip());
    }

    /**
     * method that returns the intrinsic weapon of the pokemon
     * @return
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(10, "tackle");
    }

    /**
     * Method that applies all effects during daytime
     */
    @Override
    public void dayEffect() {
        this.hurt(5);
        this.removeCapability(Status.BUFFED);
        this.removeCapability(Status.DEBUFFED);
        this.addCapability(Status.CAN_MOVE);
    }

    /**
     * Method that applies all effects during nighttime
     */
    @Override
    public void nightEffect() {
        this.heal(5);
        this.removeCapability(Status.BUFFED);
        this.removeCapability(Status.DEBUFFED);
        this.addCapability(Status.CAN_MOVE);
    }

    /**
     * Method that applies all effects during a solarFlare
     */
    public void solarFlareEffect() {
        this.hurt(10);
        this.removeCapability(Status.BUFFED);
        this.addCapability(Status.DEBUFFED);
        this.removeCapability(Status.CAN_MOVE);
    }

    /**
     * Method that applies all effects during a newMoon
     */
    public void newMoonEffect() {
        this.heal(10);
        this.addCapability(Status.BUFFED);
        this.removeCapability(Status.DEBUFFED);
        this.addCapability(Status.CAN_MOVE);
    }

    /**
     * method that retrieves the pokemon's evolved variant if theres any
     * @return
     */
    @Override
    public Pokemon getEvolvedVariant() {
        return null;
    }

}
