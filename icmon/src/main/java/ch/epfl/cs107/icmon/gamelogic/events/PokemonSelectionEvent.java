package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.AfterPokemonSelectionFightAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnRegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.graphics.PokemonSelectionMenu;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.PauseMenu;

public class PokemonSelectionEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private PokemonSelectionMenu selectionMenu;
    private int choice = 0;
    private ICMon.ICMonGameState state;
    private ICMonFightableActor foe;

    public PokemonSelectionEvent(ICMon.ICMonGameState state, ICMonFightableActor foe,
                                 PokemonSelectionMenu selectionMenu,
                                 ICMon.ICMonEventManager eventManager) {
        this.selectionMenu = selectionMenu;
        this.state = state;
        this.foe = foe;

        onStart(new RegisterEventAction(this, eventManager));
        onComplete(new UnRegisterEventAction(this, eventManager));
    }

    @Override
    public void update(float deltaTime) {
        if (this.selectionMenu.choice() >= 0) {
            this.choice = selectionMenu.choice();
            onComplete(new AfterPokemonSelectionFightAction(state, choice, foe));
            complete();
        }
    }

    @Override
    public PauseMenu getPauseMenu() {
        return this.selectionMenu;
    }
}
