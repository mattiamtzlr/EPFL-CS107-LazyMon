package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.play.engine.PauseMenu;

public class AssignPauseMenuAction implements Action {
    private ICMon.ICMonGameState state;
    private PauseMenu pauseMenu;

    public AssignPauseMenuAction(ICMon.ICMonGameState state, PauseMenu pauseMenu) {
        this.state = state;
        this.pauseMenu = pauseMenu;
    }

    @Override
    public void perform() {
        state.newPauseMenu(pauseMenu);
    }
}
