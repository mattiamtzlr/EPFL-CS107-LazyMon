package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Voltball extends Pokemon {
    public Voltball(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "voltball", 2, 15,
                new AttackAction(), new EscapeAction());
    }
}
