package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;

public class EndPauseMenuAction implements Action {
    private ICMon.ICMonGameState state;

    public EndPauseMenuAction(ICMon.ICMonGameState state) {
        this.state = state;
    }

    @Override
    public void perform() {
        state.endPauseMenu();
    }
}
