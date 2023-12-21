package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.ICMon;

public interface ICMonFightableActor {
    /**
     * Starts a fight between this actor and another.
     * @param foe The foe (opponent) to be fought. (ICMonFightableActor)
     * @param state The game state to be used. (ICMon.ICMonGameState)
     */
    void fight(ICMonFightableActor foe, ICMon.ICMonGameState state);
}
