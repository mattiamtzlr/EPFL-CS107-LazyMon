package ch.epfl.cs107.icmon.gamelogic.fights.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class ShockAction implements ICMonFightAction {
    // does a lot of damage, but leaves the pokemon too tired to fight the next round.
    private Pokemon user;

    public ShockAction(Pokemon user) {
        this.user = user;
    }

    @Override
    public String name() {
        return "Shock";
    }

    @Override
    public boolean doAction(Pokemon target) {
        target.sufferDamage(40);
        this.user.setTired(true);
        return true;
    }
}
