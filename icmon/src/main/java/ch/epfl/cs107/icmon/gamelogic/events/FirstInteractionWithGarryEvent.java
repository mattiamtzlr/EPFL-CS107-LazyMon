package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.NPCActor;
import ch.epfl.cs107.icmon.gamelogic.actions.GivePokemonAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class FirstInteractionWithGarryEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private ICMon.ICMonGameState state;
    private Garry garry;
    public FirstInteractionWithGarryEvent(ICMon.ICMonEventManager eventManager, ICMon.ICMonGameState state) {
        super(eventManager);
        this.state = state;
        this.onComplete(new GivePokemonAction(state, "voltball"));
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        state.openDialog(new Dialog("go_fight_garry"));
    }

    public void interactWith(Garry garry, boolean isCellInteraction) {
        this.garry = garry;
        if (state.playerHasPokemon()) {
            garry.fight(null, state);
        }
        else
            state.openDialog(new Dialog("no_pokemon_garry"));
    }

    @Override
    public void update(float deltaTime) {
        if (garry != null && garry.isDefeated())
            this.complete();

    }
}

