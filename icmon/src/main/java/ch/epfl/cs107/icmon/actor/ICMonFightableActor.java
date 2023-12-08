package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.ICMon;

public interface ICMonFightableActor {
    void fight(ICMonFightableActor foe, ICMon.ICMonGameState state);
}
