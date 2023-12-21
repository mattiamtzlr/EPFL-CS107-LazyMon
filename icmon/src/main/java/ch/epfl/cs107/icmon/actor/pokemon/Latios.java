package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Latios extends Pokemon{
    /**
     * Constructor for Latios, passes everything to super().
     * @param ownerArea Area which this Latios belongs to. (Area)
     * @param position Position at which this Latios lives. (DiscreteCoordinates)
     */
    public Latios(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "latios", 7, 60, new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()));
    }
}
