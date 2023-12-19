package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.LeaveAreaAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnRegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;

public class PokemonFightEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private ICMonFight combat;
    public PokemonFightEvent(ICMon.ICMonGameState state, ICMonFight combat, ICMon.ICMonEventManager eventManager) {
        super(eventManager);
        this.combat = combat;
    }

    @Override
    public void update(float deltaTime) {
        if (!combat.isRunning())
            complete();
    }

    public ICMonFight getPauseMenu() {
        return combat;
    }
}
