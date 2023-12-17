package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class IndependentDialogMessage extends GamePlayMessage{
    private String path;
    public IndependentDialogMessage(String path) {
        this.path = path;
    }

    @Override
    public void process(ICMon.ICMonGameState state) {
        state.openDialog(new Dialog(this.path));
    }
}
