package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.play.engine.PauseMenu;

public class AssignPauseMenuAction implements Action {
    private final ICMon.ICMonGameState state;
    private final PauseMenu pauseMenu;

    /**
     * Allows the assignment of a pause menu from an event.
     * @param state The game's state. (ICMon.ICMonGameState)
     * @param pauseMenu The pause menu to be used. (PauseMenu)
     */
    public AssignPauseMenuAction(ICMon.ICMonGameState state, PauseMenu pauseMenu) {
        this.state = state;
        this.pauseMenu = pauseMenu;
    }

    @Override
    public void perform() {
        state.newPauseMenu(pauseMenu);
    }
}
