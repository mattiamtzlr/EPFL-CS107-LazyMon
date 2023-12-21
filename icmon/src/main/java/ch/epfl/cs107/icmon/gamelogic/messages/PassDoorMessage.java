package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.Door;

public class PassDoorMessage extends GamePlayMessage{
    private final Door door;

    /**
     * Allows the player to request the passage of a door.
     * @param door The door which needs to be passed. (Door)
     */
    public PassDoorMessage(Door door) {
        this.door = door;
    }

    @Override
    public void process(ICMon.ICMonGameState state) {
        state.switchArea(door.getDestinationArea(), door.getDestinationCoords());
    }
}
