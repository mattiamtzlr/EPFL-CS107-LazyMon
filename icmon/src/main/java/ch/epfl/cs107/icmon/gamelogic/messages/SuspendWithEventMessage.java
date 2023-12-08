package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class SuspendWithEventMessage extends GamePlayMessage{
    private ICMonEvent event;
    private boolean hasPauseMenu;
    public SuspendWithEventMessage(ICMonEvent event, boolean hasPauseMenu) {
        this.event = event;
        this.hasPauseMenu = hasPauseMenu;
    }

    @Override
    public void process(ICMon.ICMonGameState state) {
        state.suspendOtherEvents(event, hasPauseMenu);
    }
}
