package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class RegisterEventAction implements Action{
    private final ICMonEvent event;
    private final ICMon.ICMonEventManager eventManager;

    /**
     * Registers a new event in the game's events.
     * @param event The event to be registered. (ICMonEvent)
     * @param eventManager The event manager of the game. (ICMon.ICMonEventManager)
     */
    public RegisterEventAction(ICMonEvent event, ICMon.ICMonEventManager eventManager) {
        this.event = event;
        this.eventManager = eventManager;
    }
    @Override
    public void perform() {
        if (!event.isStarted())
            eventManager.registerEvent(event);
    }
}
