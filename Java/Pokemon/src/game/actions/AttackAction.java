package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.pokemon.Pokemon;
import game.status.Status;

import java.util.Random;

/**
 * An Action to attack another Actor.
 * Created by:
 *
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Louis Meng Hoe Chow
 */
public class AttackAction extends Action {

    /**
     * The Actor that is to be attacked
     */
    protected Actor target;

    /**
     * The direction of incoming attack.
     */
    protected String direction;

    /**
     * Random number generator
     */
    protected Random rand = new Random();

    /**
     * Constructor.
     *
     * @param target the Actor to attack
     */
    public AttackAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    @Override
    public String execute(Actor actor, GameMap map) {

        boolean targetMissed = false;
        String result = "";
        Weapon weapon = actor.getWeapon();

        if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
            targetMissed = true;
            result = actor + " misses " + target + ".";
        }

        if (!targetMissed) {
            int damage = weapon.damage();
            String extraResult = "";

            if (actor.hasCapability(Status.BUFFED)) {
                damage = (int) (damage*1.2);
                extraResult = " It finds itself strengthened by something.";
            } else if (actor.hasCapability(Status.DEBUFFED)) {
                damage = (int) (damage*0.8);
                extraResult = " It finds itself weakened by something.";
            }

            result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage." + extraResult;
            target.hurt(damage);
            if (!target.isConscious()) {
                ActionList dropActions = new ActionList();
                // drop all items
                for (Item item : target.getInventory())
                    dropActions.add(item.getDropAction(actor));
                for (Action drop : dropActions)
                    drop.execute(target, map);
                // remove actor
                map.removeActor(target);
                result += System.lineSeparator() + target + " is killed.";
            }
        }

        return result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target + " at " + direction;
    }
}
