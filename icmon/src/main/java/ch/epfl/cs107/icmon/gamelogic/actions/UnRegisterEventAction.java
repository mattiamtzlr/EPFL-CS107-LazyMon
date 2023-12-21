package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class UnRegisterEventAction implements Action{
    private final ICMonEvent event;
    private final ICMon.ICMonEventManager eventManager;

    /**
     * Removes (unregisters) an event from the game's events.
     * @param event The event to be removed. (ICMonEvent)
     * @param eventManager The event manager of the game. (ICMon.ICMonEventManager)
     */
    public UnRegisterEventAction(ICMonEvent event, ICMon.ICMonEventManager eventManager) {
        this.event = event;
        this.eventManager = eventManager;
    }

    @Override
    public void perform() {
        eventManager.unRegisterEvent(event);
    }
}
