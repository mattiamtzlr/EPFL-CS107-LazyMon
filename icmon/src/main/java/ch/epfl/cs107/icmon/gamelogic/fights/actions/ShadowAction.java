package ch.epfl.cs107.icmon.gamelogic.fights.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class ShadowAction implements ICMonFightAction {
    private final double CRITICAL_HEALTH_THRESHHOLD = 0.2;
    private final int damage;
    @Override
    public String name() {
        return "Shadow Punch";
    }

    public ShadowAction(int damage) {
        this.damage = damage;
    }

    /**
     * This attack has the potential to deal massive damage, given the enemy has low health points.
     * Otherwise, it will deal slightly reduced normal damage
     * @param target The target to suffer the consequences of the action. (Pokemon)
     * @return true, since this is an attack action
     */
    @Override
    public boolean doAction(Pokemon target) {
        if (target.properties().hp() < (int)(target.properties().maxHp() * CRITICAL_HEALTH_THRESHHOLD))
            target.sufferDamage(20);
        else
            target.sufferDamage(damage-2);
        return true;
    }
}
