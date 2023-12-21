package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class CompleteEventAction implements Action{
    private final ICMonEvent event;

    /**
     * Completes the given event.
     * @param event The event to be completed. (ICMonEvent)
     */
    public CompleteEventAction(ICMonEvent event) {
        this.event = event;
    }

    @Override
    public void perform() {
        event.complete();
    }
}
