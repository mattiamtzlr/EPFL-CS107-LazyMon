package ch.epfl.cs107.icmon.gamelogic.fights.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class AttackAction implements ICMonFightAction {
    private final int damage;
    @Override
    public String name() {
        return "Tackle";
    }

    /**
     * Standard attack, which every Pokémon possesses.
     * @param damage The amount of damage inflicted on the target. (int)
     */
    public AttackAction(int damage) {
        this.damage = damage;
    }

    @Override
    public boolean doAction(Pokemon target) {
        target.sufferDamage(this.damage);
        return true;
    }
}
