package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

public class AfterWildFightAction implements Action {
    private final ICMon.ICMonGameState state;
    private final Pokemon foe;

    public AfterWildFightAction(ICMon.ICMonGameState state, Pokemon foe) {
        this.state = state;
        this.foe = foe;
    }

    @Override
    public void perform() {
        state.resetPlayerCounter();
        if (!foe.properties().isAlive())
            state.givePlayerPokemon(foe.properties().name());
        System.out.println(true);
    }
}
