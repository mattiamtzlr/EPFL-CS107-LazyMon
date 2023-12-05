package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class RegisterEventAction implements Action{
    private ICMonEvent event;
    private final ICMon.ICMonEventManager eventManager;
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
