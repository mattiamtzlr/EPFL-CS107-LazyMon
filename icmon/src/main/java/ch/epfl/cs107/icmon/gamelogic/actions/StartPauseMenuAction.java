package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;

public class StartPauseMenuAction implements Action {
    private ICMon.ICMonGameState state;

    public StartPauseMenuAction(ICMon.ICMonGameState state) {
        this.state = state;
    }

    @Override
    public void perform() {
        state.startPauseMenu();
    }
}
