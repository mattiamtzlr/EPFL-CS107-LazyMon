package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.Door;

public class PassDoorMessage extends GamePlayMessage{
    private Door door;

    public PassDoorMessage(Door door) {
        this.door = door;
    }

    @Override
    public void process(ICMon.ICMonGameState state) {
        state.switchArea(door.getDestinationArea(), door.getDestinationCoords());
    }
}
