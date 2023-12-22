package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;

public class AfterPokemonSelectionFightAction implements Action {
    private final ICMon.ICMonGameState state;
    private final int choice;
    private final ICMonFightableActor foe;
    private final boolean wild; // keeps track if it is a wild encounter

    /**
     * Allows the game to continue after having selected a Pokémon before a fight.
     * @param state The game's state. (ICMon.ICMonGameState)
     * @param choice The index of the chosen Pokémon. (int)
     * @param foe The opponent in the fight. (ICMonFightableActor)
     */
    public AfterPokemonSelectionFightAction(ICMon.ICMonGameState state, int choice,
                                            ICMonFightableActor foe, boolean wild) {
        this.state = state;
        this.choice = choice;
        this.foe = foe;
        this.wild = wild;
    }

    @Override
    public void perform() {
        state.startFightEvent(this.choice, this.foe, this.wild);
    }
}
