package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Latios extends Pokemon{
    public Latios(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "latios", 7, 60, new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()));
    }
}
