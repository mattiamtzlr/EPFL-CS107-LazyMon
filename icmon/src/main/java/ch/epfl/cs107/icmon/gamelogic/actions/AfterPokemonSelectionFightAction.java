package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

public class AfterPokemonSelectionFightAction implements Action {
    private final ICMon.ICMonGameState state;
    private final int choice;
    private final ICMonFightableActor foe;

    /**
     * Allows the game to continue after having selected a Pokémon before a fight.
     * @param state The game's state. (ICMon.ICMonGameState)
     * @param choice The index of the chosen Pokémon. (int)
     * @param foe The opponent in the fight. (ICMonFightableActor)
     */
    public AfterPokemonSelectionFightAction(ICMon.ICMonGameState state, int choice,
                                            ICMonFightableActor foe) {
        this.state = state;
        this.choice = choice;
        this.foe = foe;
    }

    @Override
    public void perform() {
        state.startFightEvent(this.choice, this.foe);
    }
}
