package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;

/**
 * Generic message, specific implementations of which can be sent to the game.
 */
public abstract class GamePlayMessage {
    /**
     * Defines what this message does.
     * @param state The game's state. (ICMon.ICMonGameState)
     */
    public void process(ICMon.ICMonGameState state){}
}
