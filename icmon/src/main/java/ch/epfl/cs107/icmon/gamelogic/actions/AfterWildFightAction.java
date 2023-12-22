package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;

public class ResetPlayerCounterAction implements Action {
    private final ICMon.ICMonGameState state;

    public ResetPlayerCounterAction(ICMon.ICMonGameState state) {
        this.state = state;
    }

    @Override
    public void perform() {
        state.resetPlayerCounter();
        System.out.println(true);
    }
}
