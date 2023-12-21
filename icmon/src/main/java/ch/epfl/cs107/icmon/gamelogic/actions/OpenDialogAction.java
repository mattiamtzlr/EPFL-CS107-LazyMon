package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class OpenDialogAction implements Action{
    private final ICMon.ICMonGameState state;
    private final Dialog dialog;

    /**
     * Opens and displays a new dialog.
     * @param state The game's state. (ICMon.ICMonGameState)
     * @param dialog The dialog to be displayed. (Dialog)
     */
    public OpenDialogAction(ICMon.ICMonGameState state, Dialog dialog){
        this.state = state;
        this.dialog = dialog;
    }
    @Override
    public void perform() {
        state.openDialog(dialog);
    }
}
