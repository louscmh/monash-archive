package game.pokemon;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.elements.ElementsHelper;
import game.weapons.Bubble;
import game.elements.Element;
import game.status.Status;
import game.behaviours.Behaviour;
import game.time.TimePerception;
import game.weapons.Ember;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by:
 * @author Kerk Han Chin
 * Modified by:
 * @author Louis Meng Hoe Chow
 */
public class Squirtle extends Pokemon implements TimePerception {
    private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour

    public Squirtle() {
        super("Squirtle", 's', 100);
        // HINT: add more relevant behaviours here
        this.addCapability(Element.WATER);
        this.addCapability(Status.CATCHABLE);
        this.registerInstance();
        this.addCapability(Status.CAN_MOVE);
        this.addSpecialAttack(new Bubble());
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
        this.hurt(10);
        this.removeCapability(Status.BUFFED);
        this.removeCapability(Status.DEBUFFED);
        this.addCapability(Status.CAN_MOVE);

    }

    /**
     * Method that applies all effects during nighttime
     */
    @Override
    public void nightEffect() {
        this.heal(10);
        this.removeCapability(Status.BUFFED);
        this.removeCapability(Status.DEBUFFED);
        this.addCapability(Status.CAN_MOVE);

    }

    /**
     * Method that applies all effects during a solarFlare
     */
    public void solarFlareEffect() {
        this.hurt(15);
        this.removeCapability(Status.BUFFED);
        this.addCapability(Status.DEBUFFED);
        this.removeCapability(Status.CAN_MOVE);

    }

    /**
     * Method that applies all effects during a newMoon
     */
    public void newMoonEffect() {
        this.heal(15);
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

    /**
     * an overwrite on checkSpecialCondition as theres an extra condition for squirtle being that if the target pokemon has fire element it passes
     * @param map
     * @param targetPokemon
     * @return
     */
    @Override
    public boolean checkSpecialCondition(GameMap map, Actor targetPokemon) {
        Ground currentGround = map.locationOf(this).getGround();
        return ElementsHelper.hasAnySimilarElements(this, currentGround.findCapabilitiesByType(Element.class)) || targetPokemon.hasCapability(Element.FIRE);
    }
}
