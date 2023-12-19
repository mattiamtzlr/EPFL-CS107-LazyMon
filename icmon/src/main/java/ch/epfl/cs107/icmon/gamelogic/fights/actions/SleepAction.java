package ch.epfl.cs107.icmon.gamelogic.fights.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class SleepAction implements ICMonFightAction {
    // regenerates Pokemon 15 hp points
    private Pokemon user;

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
