package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;

public class EndPauseMenuAction implements Action {
    private final ICMon.ICMonGameState state;

    /**
     * Removes / disables the current pause menu.
     * @param state The game's state. (ICMon.ICMonGameState)
     */
    public EndPauseMenuAction(ICMon.ICMonGameState state) {
        this.state = state;
    }

    @Override
    public void perform() {
        state.endPauseMenu();
    }
}
