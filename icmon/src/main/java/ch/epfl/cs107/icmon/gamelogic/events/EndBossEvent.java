package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class EndBossEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private final ICMon.ICMonGameState state;

    /**
     * This event gets triggered when the player talks to the Shop Assistant after having defeated Garry. She tells the player
     * to help her find her lost Pikachu. An epic quest begins... :)
     * TODO (Leo): is this correct lol?
     * @param eventManager The event manager of the game. (ICMon.ICMonEventManager)
     * @param state The game's state. (ICMon.ICMonGameState)
     */
    public EndBossEvent(ICMon.ICMonEventManager eventManager, ICMon.ICMonGameState state) {
        super(eventManager);
        this.state = state;
    }
    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        this.state.openDialog(new Dialog("end_boss_interaction_icshopassistant"));
    }

    @Override
    public void update(float deltaTime) {

    }
}
