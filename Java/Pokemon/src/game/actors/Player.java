package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import game.status.Status;
import game.actions.SummonPokemonAction;
import game.affection.AffectionManager;
import game.time.TimePerceptionManager;

/**
 * Class representing the Player.
 *
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Kerk Han Chin
 *
 */
public class Player extends Actor {

	private final Menu menu = new Menu();
	private final TimePerceptionManager timePerceptionManager;
	private final AffectionManager affectionManager;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(Status.IMMUNE);
		this.addCapability(Status.CAN_CATCH_POKEMON);
		timePerceptionManager = TimePerceptionManager.getInstance();
		affectionManager = AffectionManager.getInstance();
		affectionManager.registerTrainer(this);
	}

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		String inventoryString = "inventory: [";
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// condition to check if SummonPokemonAction can be added into the player's ActionList
		if (this.hasCapability(Status.CAN_SUMMON_POKEMON)) { // if the player has a pokeball, it automatically has this status.
			for(Item item : this.getInventory()){
				if(item.hasCapability(Status.CAN_SUMMON_POKEMON)){
					actions.add(new SummonPokemonAction(item));
				}
			}
		}

		// Loop through the inventory and display it
		for (Item item : this.getInventory()){
			inventoryString += item + ", ";
		}

		if (!this.getInventory().isEmpty()) {
			inventoryString = inventoryString.substring(0, inventoryString.length() - 2);
		}
		inventoryString += "]";
		System.out.println(inventoryString);

		timePerceptionManager.run();
		// return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	@Override
	public char getDisplayChar() {
		return super.getDisplayChar();
	}
}
