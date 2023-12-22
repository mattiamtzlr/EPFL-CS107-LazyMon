package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.ConfuseAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Kadabra extends Pokemon {
    /**
     * Constructor for Kadabra, passes everything to super().
     * @param ownerArea Area which this Kadabra belongs to. (Area)
     * @param position Position at which this Kadabra lives. (DiscreteCoordinates)
     */
    public Kadabra(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "kadabra", 15, 110, new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()), 0);
        this.addFightAction(new ConfuseAction(), 1);
    }
}
