package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Enton extends Pokemon {
    /**
     * Constructor for Enton, passes everything to super().
     * @param ownerArea Area which this Enton belongs to. (Area)
     * @param position Position at which this Enton lives. (DiscreteCoordinates)
     */
    public Enton(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "enton", 5, 120, new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()), 0);
    }
}
