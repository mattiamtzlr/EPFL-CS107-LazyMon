package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class FirstInteractionWithProfessorOakEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private final ICMon.ICMonGameState state;
    private final Dialog dialog;

    /**
     * This event is one of the first in the game and prompts the player to go talk to Professor Oak.
     * @param eventManager The event manager of the game. (ICMon.ICMonEventManager)
     * @param state The game's state. (ICMon.ICMonGameState)
     */
    public FirstInteractionWithProfessorOakEvent(ICMon.ICMonEventManager eventManager, ICMon.ICMonGameState state) {
        super(eventManager);
        this.state = state;
        this.dialog = new Dialog("first_interaction_with_prof_oak");
    }
    @Override
    public void interactWith(ProfOak oak, boolean isCellInteraction) {
        this.state.openDialog(dialog);
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellinteraction){
        this.state.openDialog(new Dialog("first_interaction_with_oak_event_icshopassistant"));
    }

    @Override
    public void update(float deltaTime) {
        if(dialog.isCompleted())
            this.complete();
    }
}
