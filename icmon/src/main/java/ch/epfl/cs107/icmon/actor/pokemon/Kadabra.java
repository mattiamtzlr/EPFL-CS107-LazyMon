package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Kadabra extends Pokemon {
    public Kadabra(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "kadabra", 15, 110, new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()), 0);
    }
}
