package ch.epfl.cs107.icmon.gamelogic.fights.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class SleepAction implements ICMonFightAction {
    // regenerates Pokémon 15 hp points
    private final Pokemon user;

    /**
     * This action allows the Pokémon to regenerate 20 health points. No other attack is performed.
     * @param user The Pokémon which uses this action; The one that regenerates. (Pokemon)
     */
    public SleepAction(Pokemon user) {
        this.user = user;
    }

    @Override
    public String name() {
        return "Sleep";
    }

    @Override
    public boolean doAction(Pokemon target) {
        this.user.heal(20);
        return true;
    }
}
