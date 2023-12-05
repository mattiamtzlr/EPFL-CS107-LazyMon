package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnRegisterEventAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;

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
        System.out.println("you did it :)");
        this.complete();
    }
}
