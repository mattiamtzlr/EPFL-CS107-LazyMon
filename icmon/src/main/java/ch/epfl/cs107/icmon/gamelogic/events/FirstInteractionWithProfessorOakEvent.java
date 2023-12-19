package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.actor.pokemon.Bulbasaur;
import ch.epfl.cs107.icmon.gamelogic.actions.GivePokemonAction;
import ch.epfl.cs107.icmon.gamelogic.actions.OpenDialogAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class FirstInteractionWithProfessorOakEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private ICMon.ICMonGameState state;
    private Dialog dialog;
    public FirstInteractionWithProfessorOakEvent(ICMon.ICMonEventManager eventManager, ICMon.ICMonGameState state) {
        super(eventManager);
        this.state = state;
        this.onStart(new OpenDialogAction(state, new Dialog("first_interaction_with_oak_event_icshopassistant")));
        this.dialog = new Dialog("first_interaction_with_prof_oak");
    }
    @Override
    public void interactWith(ProfOak oak, boolean isCellInteraction) {
        this.state.openDialog(dialog);
    }

    @Override
    public void update(float deltaTime) {
        if(dialog.isCompleted())
            this.complete();
    }
}
