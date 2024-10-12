package game.pokemon;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.behaviours.Behaviour;
import game.behaviours.WanderBehaviour;
import game.elements.Element;
import game.elements.ElementsHelper;
import game.status.Status;
import game.time.TimePerception;
import game.weapons.Blaze;
import game.weapons.Ember;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by:
 *
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Louis Meng Hoe Chow
 */
public class Charmeleon extends Pokemon implements TimePerception {

    /**
     * Constructor.
     */
    public Charmeleon() {
        super("Charmeleon", 'C', 150);
        // HINT: add more relevant behaviours here
        this.addCapability(Element.FIRE);
        this.addCapability(Status.CAN_EVOLVE);
        this.registerInstance();
        this.addSpecialAttack(new Ember());
        this.addSpecialAttack(new Blaze());
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
    @Override
    public void solarFlareEffect() {
        this.heal(15);
        this.addCapability(Status.BUFFED);
        this.removeCapability(Status.DEBUFFED);
        this.addCapability(Status.CAN_MOVE);
    }

    /**
     * Method that applies all effects during a newMoon
     */
    @Override
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
        return new Charizard();
    }

}
