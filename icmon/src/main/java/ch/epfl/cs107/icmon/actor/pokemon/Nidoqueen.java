package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Nidoqueen extends Pokemon{
    /**
     * Constructor for Nidoqueen, passes everything to super().
     * @param ownerArea Area which this Nidoqueen belongs to. (Area)
     * @param position Position at which this Nidoqueen lives. (DiscreteCoordinates)
     */
    public Nidoqueen(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "nidoqueen", 7, 60, new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()));
    }
}
