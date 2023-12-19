package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.*;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class CollectItemEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private ICMon.ICMonGameState state;
    private ICMonItem item;

    public CollectItemEvent(ICMon.ICMonGameState state, ICMonItem item, ICMon.ICMonEventManager eventManager) {
        super(eventManager);
        this.state = state;
        this.item = item;
        this.onComplete(new GivePokemonAction(state, "bulbasaur"));
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        this.state.openDialog(new Dialog("collect_item_event_interaction_with_icshopassistant"));
    }

    @Override
    public void update(float deltaTime) {
        if (item.isCollected()) {
            this.complete();
        }
    }
}
