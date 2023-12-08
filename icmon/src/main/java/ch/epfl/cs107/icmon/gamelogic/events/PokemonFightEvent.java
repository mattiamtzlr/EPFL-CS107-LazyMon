package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.LeaveAreaAction;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;

public class PokemonFightEvent extends ICMonEvent implements ICMonInteractionVisitor {

    private ICMonFight combat;
    public PokemonFightEvent(ICMonPlayer player, ICMonFight combat) {
        super(player);
        this.combat = combat;

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        combat.update(deltaTime);
        if (!combat.isRunning())
            complete();
    }

    public ICMonFight getPauseMenu() {
        return combat;
    }
}
