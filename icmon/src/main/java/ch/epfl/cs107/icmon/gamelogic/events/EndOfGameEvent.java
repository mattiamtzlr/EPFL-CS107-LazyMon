package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnRegisterEventAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class EndOfGameEvent extends ICMonEvent implements ICMonInteractionVisitor {


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

    }

    public EndOfGameEvent(ICMonPlayer player, ICMon.ICMonEventManager eventManager) {
        super(player);
        this.onStart(new RegisterEventAction(this, eventManager));
        this.onStart(new LogAction("now try to talk to the person over there ->"));
        this.onComplete(new UnRegisterEventAction(this, eventManager));
        this.onComplete(new LogAction("done:)"));
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        getPlayer().openDialog(new Dialog("end_of_game_event_interaction_with_icshopassistant"));
    }
}
