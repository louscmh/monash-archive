package game.pokemon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.weapons.BackupWeapons;
import game.actions.EvolveAction;
import game.affection.AffectionCheck;
import game.actions.FeedPokemonPokefruitAction;
import game.elements.Element;
import game.elements.ElementsHelper;
import game.status.Status;
import game.actions.CatchPokemonAction;
import game.behaviours.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by:
 * @author Kerk Han Chin
 * Modified by:
 * @author Louis Meng Hoe Chow
 */
public abstract class Pokemon extends Actor implements AffectionCheck {

    //FIXME: Change it to a sorted map (is it TreeMap? HashMap? LinkedHashMap?)
    private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour
    private final BackupWeapons specialAttacks = new BackupWeapons();

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Pokemon(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.registerPokemon(this);
        this.behaviours.put(1, new EvolutionBehaviour());
        this.behaviours.put(10, new WanderBehaviour()); // WanderBehavior as default
        this.behaviours.put(2, new SpecialAttackBehavior()); // SpecialAttack as most important
        this.behaviours.put(3, new AttackBehaviour()); // NormalAttack otherwise
        this.behaviours.put(4, new FollowBehaviour(this.getTrainer()));
    }

    /**
     * @param otherActor the Actor that might perform an action.
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // condition to check if CatchPokemonAction should be added into their allowableActions
        if (otherActor.hasCapability(Status.CAN_CATCH_POKEMON) && this.hasCapability(Status.CATCHABLE)) {
            actions.add(new CatchPokemonAction(this, direction));
        }

        // condition to check if FeedPokemonPokefruitAction should be added into their allowableActions
        if (otherActor.hasCapability(Status.CAN_FEED_POKEMON_FIRE_POKEFRUIT) || otherActor.hasCapability(Status.CAN_FEED_POKEMON_WATER_POKEFRUIT) || otherActor.hasCapability(Status.CAN_FEED_POKEMON_GRASS_POKEFRUIT)) {
            for(Item item : otherActor.getInventory()){
                if(item.hasCapability(Status.CAN_FEED_POKEMON_FIRE_POKEFRUIT) || item.hasCapability(Status.CAN_FEED_POKEMON_WATER_POKEFRUIT) || item.hasCapability(Status.CAN_FEED_POKEMON_GRASS_POKEFRUIT)){
                    actions.add(new FeedPokemonPokefruitAction(this, item));
                }
            }
        }

        // condition to check if EvolveAction should be added into their allowableActions
        if (this.getAffectionPoint(this) == 100 && this.hasCapability(Status.CAN_EVOLVE)) {
            actions.add(new EvolveAction(this));
        }

        // return ActionList
        return actions;
    }

    /**
     * By using behaviour loops, it will decide what will be the next action automatically.
     *
     * @see Actor#playTurn(ActionList, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * toString method that displays the pokemon's name and HP
     * @return
     */
    @Override
    public String toString() {
        return name + this.printHp();
    }

    /***
     * getter for behaviors
     * @return
     */
    public Map<Integer, Behaviour> getBehaviours() {
        return this.behaviours;
    }

    /***
     * setter for behaviors, used for potential subclasses
     * @param priority
     * @param newBehavior
     */
    public void addBehaviour(int priority, Behaviour newBehavior) {
        this.behaviours.put(priority, newBehavior);
    }

    /**
     * setter for SpecialAttack, used for subclasses to add their unique WeaponItem
     * @param specialAttack
     */
    public void addSpecialAttack(WeaponItem specialAttack) {
        this.specialAttacks.addWeapon(specialAttack);
    }

    /**
     * @param isEquipping
     */
    public void toggleWeapon(boolean isEquipping) {
        if (isEquipping){
            this.addItemToInventory(this.specialAttacks.retrieveRandomWeapon());
        }
        else {
            for (WeaponItem weapon : this.specialAttacks.getWeaponList()) {
                removeItemFromInventory(weapon);
            }
        }
    }

    /***
     * getter for name
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * abstract method to allow pokemons to retrieve their evolved variants if there is one
     * @return
     */
    public abstract Pokemon getEvolvedVariant();

    /**
     * method to check if the pokemon has satisfied all the conditions to equip a Weapon, can be overwritten
     * @param map
     * @param targetPokemon
     * @return
     */
    public boolean checkSpecialCondition(GameMap map, Actor targetPokemon) {
        Ground currentGround = map.locationOf(this).getGround();
        return ElementsHelper.hasAnySimilarElements(this, currentGround.findCapabilitiesByType(Element.class));
    }
}

