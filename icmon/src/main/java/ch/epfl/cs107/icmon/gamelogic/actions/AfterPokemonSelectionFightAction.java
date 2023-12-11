package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

public class AfterPokemonSelectionFightAction implements Action {
    private ICMon.ICMonGameState state;
    private int choice;
    private ICMonFightableActor foe;

    public AfterPokemonSelectionFightAction(ICMon.ICMonGameState state, int choice,
                                            ICMonFightableActor foe) {
        this.state = state;
        this.choice = choice;
        System.out.println(this.choice + " in AfterPokemonSelectionFightAction");
        this.foe = foe;
    }

    @Override
    public void perform() {
        state.startFightEvent(this.choice, this.foe);
    }
}
