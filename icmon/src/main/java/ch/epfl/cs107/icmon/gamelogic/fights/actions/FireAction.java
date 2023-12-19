package ch.epfl.cs107.icmon.gamelogic.fights.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class FireAction implements ICMonFightAction {
    // sets target on fire, which causes it to take damage every following round.

    @Override
    public String name() {
        return "Fire";
    }

    @Override
    public boolean doAction(Pokemon target) {
        target.sufferDamage(18);
        target.setOnFire();
        return true;
    }
}
