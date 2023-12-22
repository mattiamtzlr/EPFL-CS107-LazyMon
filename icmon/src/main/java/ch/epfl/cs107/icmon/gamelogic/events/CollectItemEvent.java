package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.gamelogic.actions.*;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class CollectItemEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private final ICMon.ICMonGameState state;
    private final ICMonItem item;

    /**
     * This event prompts the player to pick up a Pokéball which contains the starter Pokémon Bulbasaur.
     * @param state The game's state. (ICMon.ICMonGameState)
     * @param item The item to be collected. (ICMonItem)
     * @param eventManager The event manager of the game. (ICMon.ICMonEventManager)
     */
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
    public void interactWith(ProfOak oak, boolean isCellInteraction) {
        this.state.openDialog(new Dialog("collect_item_event_interaction_with_oak"));
    }
    @Override
    public void interactWith(Garry garry, boolean isCellInteraction) {
        this.state.openDialog(new Dialog("collect_item_event_interaction_with_garry"));
    }
    @Override
    public void update(float deltaTime) {
        if (item.isCollected()) {
            this.complete();
        }
    }
}
