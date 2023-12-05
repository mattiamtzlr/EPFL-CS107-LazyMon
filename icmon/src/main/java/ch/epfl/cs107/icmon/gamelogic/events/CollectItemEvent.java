package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnRegisterEventAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;

public class CollectItemEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private ICMonItem item;

    public CollectItemEvent(ICMonPlayer player, ICMonItem item, ICMon.ICMonEventManager eventManager) {
        super(player);
        this.item = item;
        this.onStart(new RegisterEventAction(this, eventManager));
        this.onStart(new LogAction("Hi, try to pick up the ICBall using \"F\""));
        this.onComplete(new UnRegisterEventAction(this, eventManager));//
    }


    @Override
    public void update(float deltaTime) {
        if (item.isCollected()) {
            this.complete();
        }
    }
}
