package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.actions.AssignPauseMenuAction;
import ch.epfl.cs107.icmon.gamelogic.actions.EndPauseMenuAction;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.StartPauseMenuAction;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class SuspendWithEventMessage extends GamePlayMessage{
    private ICMonEvent event;
    public SuspendWithEventMessage(ICMonEvent event) {
        this.event = event;
    }

    @Override
    public void process(ICMon.ICMonGameState state) {
        if (event.getPauseMenu() != null) {
            event.onStart(new AssignPauseMenuAction(state, this.event.getPauseMenu()));
            event.onStart(new StartPauseMenuAction(state));
            event.onComplete(new EndPauseMenuAction(state));
            event.onComplete(new LogAction("completed fight !"));
            event.start();
        }
    }
}
