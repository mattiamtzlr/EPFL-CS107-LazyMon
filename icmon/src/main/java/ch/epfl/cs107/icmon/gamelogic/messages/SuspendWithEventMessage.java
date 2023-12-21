package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.actions.AssignPauseMenuAction;
import ch.epfl.cs107.icmon.gamelogic.actions.EndPauseMenuAction;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.StartPauseMenuAction;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class SuspendWithEventMessage extends GamePlayMessage{
    private final ICMonEvent event;

    /**
     * Suspends the game (= opens the pause menu of the provided event, if available). All current events are suspended and then
     * resumed on completion of the given event.
     * @param event The event for which the game is suspended. Should contain a pause menu. (ICMonEvent)
     */
    public SuspendWithEventMessage(ICMonEvent event) {
        this.event = event;
    }

    @Override
    public void process(ICMon.ICMonGameState state) {
        if (event.getPauseMenu() != null) {
            event.onStart(new AssignPauseMenuAction(state, this.event.getPauseMenu()));
            event.onStart(new StartPauseMenuAction(state));
            event.onComplete(new EndPauseMenuAction(state));
            event.start();
        }
    }
}
