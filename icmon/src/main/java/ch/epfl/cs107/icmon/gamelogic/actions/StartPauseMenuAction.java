package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;

public class StartPauseMenuAction implements Action {
    private final ICMon.ICMonGameState state;

    /**
     * Starts the currently assigned pause menu.
     * @param state The game's state. (ICMon.ICMonGameState)
     */
    public StartPauseMenuAction(ICMon.ICMonGameState state) {
        this.state = state;
    }

    @Override
    public void perform() {
        state.startPauseMenu();
    }
}
