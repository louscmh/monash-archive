package game.status;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 *
 * Modified by:
 * @author Kerk Han Chin
 */
public enum Status {
    IMMUNE, // an enum to identify that an object is immune to any attack.
    HOSTILE, // use this status to be considered hostile towards enemy (e.g., to be attacked by enemy)
    CATCHABLE,
    CAN_FEED_POKEMON_FIRE_POKEFRUIT,
    CAN_FEED_POKEMON_WATER_POKEFRUIT,
    CAN_FEED_POKEMON_GRASS_POKEFRUIT,
    CAN_CATCH_POKEMON,
    CAN_SUMMON_POKEMON,
    CAN_MOVE,
    CAN_EVOLVE,
    BUFFED,
    DEBUFFED
}
