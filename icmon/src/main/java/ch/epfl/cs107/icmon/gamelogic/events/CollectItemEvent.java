package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;

public class CollectItemEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private ICMonItem item;

    public CollectItemEvent(ICMonPlayer player, ICMonItem item) {
        super(player);
        this.item = item;
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        System.out.println("Interaction between player and ICShopAssistant based on events.");
    }

    @Override
    public void update(float deltaTime) {
        if (item.isCollected())
            this.complete();
    }
}
