package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

public class AfterPokemonSelectionFightAction implements Action {
    private ICMon.ICMonGameState state;
    private ICMonFight combat;
    private ICMonFightableActor foe;

    public AfterPokemonSelectionFightAction(ICMon.ICMonGameState state, ICMonFight combat,
                                            ICMonFightableActor foe) {
        this.state = state;
        this.combat = combat;
        this.foe = foe;
    }

    @Override
    public void perform() {
        state.startFightEvent(this.combat, this.foe);
    }
}
