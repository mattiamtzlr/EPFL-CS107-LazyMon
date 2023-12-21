package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class IndependentDialogMessage extends GamePlayMessage{
    private final String path;

    /**
     * Allows a dialog to be opened without any additional conditions, thus independent.
     * @param path The path to the xml file in resources/dialogs. (String)
     */
    public IndependentDialogMessage(String path) {
        this.path = path;
    }

    @Override
    public void process(ICMon.ICMonGameState state) {
        state.openDialog(new Dialog(this.path));
    }
}
