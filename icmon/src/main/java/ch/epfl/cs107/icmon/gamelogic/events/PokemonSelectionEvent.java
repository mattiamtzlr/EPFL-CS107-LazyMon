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

    public PokemonSelectionEvent(ICMonPlayer player, ICMonFightableActor foe,
                                 PokemonSelectionMenu selectionMenu, ICMonFight combat,
                                 ICMon.ICMonEventManager eventManager, ICMon.ICMonGameState state) {
        super(player);
        this.selectionMenu = selectionMenu;
        onStart(new RegisterEventAction(this, eventManager));
        onComplete(new AfterPokemonSelectionFightAction(state, combat, foe));
        onComplete(new UnRegisterEventAction(this, eventManager));
    }

    @Override
    public void update(float deltaTime) {
        selectionMenu.update(deltaTime);
        if (!selectionMenu.isRunning())
            complete();
    }

    @Override
    public PauseMenu getPauseMenu() {
        return this.selectionMenu;
    }
}
