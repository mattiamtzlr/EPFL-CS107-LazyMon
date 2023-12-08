package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.play.engine.PauseMenu;

public class ConnectPauseMenuAction implements Action {

    ICMon.ICMonGameState state;
    PauseMenu menu;
    public ConnectPauseMenuAction(ICMon.ICMonGameState state, PauseMenu menu) {
        this.state = state;
        this.menu = menu;
    }

    @Override
    public void perform() {
        state.newPauseMenu(menu);
    }
}
