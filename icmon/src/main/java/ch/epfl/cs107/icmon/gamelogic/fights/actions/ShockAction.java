package ch.epfl.cs107.icmon.gamelogic.fights.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class ShockAction implements ICMonFightAction {
    @Override
    public String name() {
        return "Shock";
    }

    @Override
    public boolean doAction(Pokemon target) {
        target.sufferDamage(target.properties().hp() + 1);
        return true;
    }
}
