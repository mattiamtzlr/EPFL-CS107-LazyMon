package ch.epfl.cs107.icmon.gamelogic.fights.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class AttackAction implements ICMonFightAction {
    private int damage;
    @Override
    public String name() {
        return "Attack";
    }

    public AttackAction(int damage) {
        this.damage = damage;
    }

    @Override
    public boolean doAction(Pokemon target) {
        target.sufferDamage(this.damage);
        return true;
    }
}
