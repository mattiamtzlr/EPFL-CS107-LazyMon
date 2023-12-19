package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class OpenDialogAction implements Action{
    private ICMon.ICMonGameState state;
    private Dialog dialog;
    public OpenDialogAction(ICMon.ICMonGameState state, Dialog dialog){
        this.state = state;
        this.dialog = dialog;
    }
    @Override
    public void perform() {
        state.openDialog(dialog);
    }
}
