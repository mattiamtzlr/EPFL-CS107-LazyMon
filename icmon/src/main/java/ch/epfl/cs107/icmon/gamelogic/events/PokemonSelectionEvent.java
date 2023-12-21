package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.gamelogic.actions.AfterPokemonSelectionFightAction;
import ch.epfl.cs107.icmon.graphics.PokemonSelectionMenu;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.PauseMenu;

public class PokemonSelectionEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private final PokemonSelectionMenu selectionMenu;
    private final ICMon.ICMonGameState state;
    private final ICMonFightableActor foe;

    /**
     * This event invokes a Pokémon selection menu, which allows to choose a Pokémon before a fight.
     * @param state The game's state. (ICMon.ICMonGameState)
     * @param foe The opponent in the fight. (ICMonFightableActor)
     * @param selectionMenu The selection menu to be used. (PokemonSelectionMenu)
     * @param eventManager The event manager of the game. (ICMon.ICMonEventManager)
     */
    public PokemonSelectionEvent(ICMon.ICMonGameState state, ICMonFightableActor foe,
                                 PokemonSelectionMenu selectionMenu,
                                 ICMon.ICMonEventManager eventManager) {
        super(eventManager);
        this.selectionMenu = selectionMenu;
        this.state = state;
        this.foe = foe;
    }

    @Override
    public void update(float deltaTime) {
        if (this.selectionMenu.choice() >= 0) {
            int choice = selectionMenu.choice();
            onComplete(new AfterPokemonSelectionFightAction(state, choice, foe));
            complete();
        }
    }

    @Override
    public PauseMenu getPauseMenu() {
        return this.selectionMenu;
    }
}