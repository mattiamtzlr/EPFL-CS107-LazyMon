package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.SleepAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Snorlax extends Pokemon {
    /**
     * Constructor for Snorlax, passes everything to super().
     * @param ownerArea Area which this Snorlax belongs to. (Area)
     * @param position Position at which this Snorlax lives. (DiscreteCoordinates)
     */
    public Snorlax(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "snorlax", 12, 120,  new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()), 0);
        this.addFightAction(new SleepAction(this), 1);
    }
}
