package game.pokemon;


import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.elements.Element;
import game.elements.ElementsHelper;
import game.weapons.Ember;
import game.status.Status;
import game.time.TimePerception;

/**
 * Created by:
 *
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Kerk Han Chin
 * @author Louis Meng Hoe Chow
 */
public class Charmander extends Pokemon implements TimePerception {

    /**
     * Constructor.
     */
    public Charmander() {
        super("Charmander", 'c', 100);
        // HINT: add more relevant behaviours here
        this.addCapability(Element.FIRE);
        this.registerInstance();
        this.addCapability(Status.CAN_MOVE);
        this.addCapability(Status.CAN_EVOLVE);
        this.addCapability(Status.CATCHABLE);
        this.addSpecialAttack(new Ember());
    }

    /**
     * method that returns the intrinsic weapon of the pokemon
     * @return
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(10, "scratch");
    }

    /**
     * Method that applies all effects during daytime
     */
    @Override
    public void dayEffect() {
        this.heal(10);
        this.removeCapability(Status.BUFFED);
        this.removeCapability(Status.DEBUFFED);
        this.addCapability(Status.CAN_MOVE);
    }

    /**
     * Method that applies all effects during nighttime
     */
    @Override
    public void nightEffect() {
        this.hurt(10);
        this.removeCapability(Status.BUFFED);
        this.removeCapability(Status.DEBUFFED);
        this.addCapability(Status.CAN_MOVE);
    }

    /**
     * Method that applies all effects during a solarFlare
     */
    public void solarFlareEffect() {
        this.heal(15);
        this.addCapability(Status.BUFFED);
        this.removeCapability(Status.DEBUFFED);
        this.addCapability(Status.CAN_MOVE);
    }

    /**
     * Method that applies all effects during a newMoon
     */
    public void newMoonEffect() {
        this.hurt(15);
        this.addCapability(Status.DEBUFFED);
        this.removeCapability(Status.BUFFED);
        this.removeCapability(Status.CAN_MOVE);
    }


    /**
     * method that retrieves the pokemon's evolved variant if theres any
     * @return
     */
    @Override
    public Pokemon getEvolvedVariant() {
        return new Charmeleon();
    }

}
