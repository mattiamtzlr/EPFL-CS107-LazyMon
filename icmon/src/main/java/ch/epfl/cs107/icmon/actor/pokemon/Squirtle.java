package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Squirtle extends Pokemon {
    /**
     * Constructor for Squirtle, passes everything to super().
     * @param ownerArea Area which this Squirtle belongs to. (Area)
     * @param position Position at which this Squirtle lives. (DiscreteCoordinates)
     */
    public Squirtle(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "squirtle", 10, 70, new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()), 0);
    }
}
