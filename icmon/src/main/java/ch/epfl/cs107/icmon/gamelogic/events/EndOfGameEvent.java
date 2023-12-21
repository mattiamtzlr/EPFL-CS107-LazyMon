package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class EndOfGameEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private final ICMon.ICMonGameState state;

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    /**
     * The final event of the game, not really defined yet.
     * @param state The game's state. (ICMon.ICMonGameState)
     * @param eventManager The event manager of the game. (ICMon.ICMonEventManager)
     */
    public EndOfGameEvent(ICMon.ICMonGameState state, ICMon.ICMonEventManager eventManager) {
        super(eventManager);
        this.state = state;
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        this.state.openDialog(new Dialog("end_of_game_event_interaction_with_icshopassistant"));
    }
}
