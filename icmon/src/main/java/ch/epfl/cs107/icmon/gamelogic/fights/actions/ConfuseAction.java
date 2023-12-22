package ch.epfl.cs107.icmon.gamelogic.fights.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

import java.util.Random;

public class ConfuseAction implements ICMonFightAction {
    private Random random = new Random();
    @Override
    public String name() {
        return "Confuse";
    }

    @Override
    public boolean doAction(Pokemon target) {
        target.sufferDamage(9);
        // will stun the targeted pokemon in 3/5 cases
        if (random.nextInt(1,6) <= 3)
            target.setTired(true);
        return true;
    }
}
