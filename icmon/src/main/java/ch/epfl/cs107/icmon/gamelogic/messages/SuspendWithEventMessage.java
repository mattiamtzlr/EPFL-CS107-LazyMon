package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class SuspendWithEventMessage extends GamePlayMessage{
    private ICMonEvent event;
    public SuspendWithEventMessage(ICMonEvent event) {
        this.event = event;
    }

    @Override
    public void process(ICMon.ICMonGameState state) {
    }
}
