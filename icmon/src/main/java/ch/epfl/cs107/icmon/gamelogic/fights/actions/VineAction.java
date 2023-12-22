package ch.epfl.cs107.icmon.gamelogic.fights.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

import java.util.Random;

public class VineAction implements ICMonFightAction {
    private final Random random = new Random();
    private final int damage;

    public VineAction(int damage) {
        this.damage = damage;
    }

    @Override
    public String name() {
        return "Vine Whip";
    }

    @Override
    public boolean doAction(Pokemon target) {
        target.sufferDamage(damage-2);
        target.setThorned(random.nextInt(2, 6));
        return true;
    }
}
