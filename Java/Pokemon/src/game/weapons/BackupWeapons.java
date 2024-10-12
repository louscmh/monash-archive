package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actions.AttackAction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Louis Meng Hoe Chow
 *
 * TODO: Use this class to store Pokemon's weapons (special attack) permanently.
 * If a Pokemon needs to use a weapon, put it into that Pokemon's inventory.
 * @see Actor #getWeapon() method.
 * @see AttackAction uses getWeapon() in the execute() method.
 */
public class BackupWeapons {
    private List<WeaponItem> weaponList = new ArrayList<WeaponItem>();
    private Random random = new Random();

    public List<WeaponItem> getWeaponList() {
        return weaponList;
    }

    public void addWeapon(WeaponItem weaponItem) {
        weaponList.add(weaponItem);
    }

    public WeaponItem retrieveRandomWeapon() {
        int randomIndex = random.nextInt(weaponList.size());
        return weaponList.get(randomIndex);
    }
}
