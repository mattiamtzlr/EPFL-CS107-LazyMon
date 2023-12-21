package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Voltball extends Pokemon {
    /**
     * Constructor for Voltball, passes everything to super().
     * @param ownerArea Area which this Voltball belongs to. (Area)
     * @param position Position at which this Voltball lives. (DiscreteCoordinates)
     */
    public Voltball(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "voltball", 8, 60,
                new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()), 0);
    }
}
