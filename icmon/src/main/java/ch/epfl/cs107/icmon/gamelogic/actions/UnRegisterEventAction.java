package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class UnRegisterEventAction implements Action{
    private ICMonEvent event;
    private ICMon.ICMonEventManager eventManager;
    public UnRegisterEventAction(ICMonEvent event, ICMon.ICMonEventManager eventManager) {
        this.event = event;
        this.eventManager = eventManager;
    }

    @Override
    public void perform() {
        eventManager.unRegisterEvent(event);
    }
}
