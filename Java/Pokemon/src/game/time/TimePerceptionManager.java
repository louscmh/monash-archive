package game.time;

import java.util.ArrayList;
import java.util.List;

/**
 * A global Singleton manager that gives time perception  on the affected instances.
 * TODO: you may modify (add or remove) methods in this class if you think they are not necessary.
 * HINT: refer to Bootcamp Week 5 about static factory method.
 *
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Kerk Han Chin
 *
 */
public class TimePerceptionManager {
    /**
     * A list of polymorph instances (any classes that implements TimePerception,
     * such as, a Charmander implements TimePerception, it will be stored in here)
     */
    private final List<TimePerception> timePerceptionList;

    private int turn;

    private TimePeriod shift;

    /**
     * A singleton instance
     */
    private static TimePerceptionManager instance;

    /**
     * Get the singleton instance of time perception manager
     *
     * @return TimePerceptionManager singleton instance
     *
     * FIXME: create a singleton instance.
     */
    public static TimePerceptionManager getInstance() {
        if (instance == null) {
            instance = new TimePerceptionManager();
        }
        return instance;
    }

    /**
     * Private constructor
     */
    private TimePerceptionManager() {
        timePerceptionList = new ArrayList<>();
        shift = TimePeriod.NIGHT;
        turn = 0;
    }

    /**
     * Traversing through all instances in the list and execute them
     * By doing this way, it will avoid using `instanceof` all over the place.
     *
     * FIXME: write a relevant logic (i.e., increment turns choose day or night) and call this method once at every turn.
     */
    public void run() {
        // Probability check of 5% to determine if special event occurs
        boolean specialEvent = Math.random() <= 0.05;

        if (turn % 5 == 0) {
            if (shift == TimePeriod.NIGHT || shift == TimePeriod.NEW_MOON) { // If previous shift was NIGHT or a NEW_MOON
                if (specialEvent) { // Set shift to SOLAR_FLARE if prob check succeeded, and DAY if not
                    shift = TimePeriod.SOLAR_FLARE;
                } else {
                    shift = TimePeriod.DAY;
                }
            }
            else if (shift == TimePeriod.DAY || shift == TimePeriod.SOLAR_FLARE) { // If previous shift was DAY or a SOLAR_FLARE
                if (specialEvent) { // Set shift to NEW_MOON if prob check succeeded, and NIGHT if not
                    shift = TimePeriod.NEW_MOON;
                } else {
                    shift = TimePeriod.NIGHT;
                }
            }
        }

        System.out.println("Current Time Period is: " + shift + " (Turn " + (turn + 1) + ")");

        for (TimePerception timePerceivingInstance : timePerceptionList) {
            if (shift == TimePeriod.DAY) {
                timePerceivingInstance.dayEffect();
            }
            else if (shift == TimePeriod.NIGHT) {
                timePerceivingInstance.nightEffect();
            }
            else if (shift == TimePeriod.SOLAR_FLARE) {
                timePerceivingInstance.solarFlareEffect();
            }
            else if (shift == TimePeriod.NEW_MOON) {
                timePerceivingInstance.newMoonEffect();
            }
        }

        turn++;
    }


    /**
     * Add the TimePerception instance to the list
     * FIXME: add objInstance to the list.
     * @param objInstance any instance that implements TimePerception
     */
    public void append(TimePerception objInstance) {
        timePerceptionList.add(objInstance);
    }


    /**
     * Remove a TimePerception instance from the list
     *
     * FIXME: [OPTIONAL] run cleanUp once every turn if you don't want to
     *        have too many instances in the list (e.g., memory leak)
     * @param objInstance object instance
     */
    public void cleanUp(TimePerception objInstance) {
        timePerceptionList.remove(objInstance);
    }
}
