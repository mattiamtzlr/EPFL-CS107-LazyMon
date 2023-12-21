package ch.epfl.cs107.icmon.gamelogic.fights.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

/**
 * Action which allows the Pokémon to flee.
 */
public class EscapeAction implements ICMonFightAction {
    @Override
    public String name() {
        return "Run away";
    }

    @Override
    public boolean doAction(Pokemon target) {
        return false;
    }
}
