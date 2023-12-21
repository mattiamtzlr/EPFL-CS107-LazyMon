package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

/**
 * Generic fight action.
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public interface ICMonFightAction {
    /**
     * Returns the name of the action.
     * @return (String)
     */
    String name();

    /**
     * Performs the action on the given target.
     * @param target The target to suffer the consequences of the action. (Pokemon)
     * @return Should return true, if the action is a type of attack action and false, if it is an escape action. (boolean)
     */
    boolean doAction(Pokemon target);
}
