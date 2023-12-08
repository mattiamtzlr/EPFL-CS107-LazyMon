package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;

abstract public class GamePlayMessage {
    public void process(ICMon.ICMonGameState state){}
}
