package ch.epfl.cs107.icmon.gamelogic.fights.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class AttackAction implements ICMonFightAction {
    @Override
    public String name() {
        return "Attack";
    }

    @Override
    public boolean doAction(Pokemon target) {
        target.sufferDamage(1);
        return true;
    }
}
