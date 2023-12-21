package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.FireAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Charizard extends Pokemon {
    /**
     * Constructor for Charizard, passes everything to super().
     * @param ownerArea Area which this Charizard belongs to. (Area)
     * @param position Position at which this Charizard lives. (DiscreteCoordinates)
     */
    public Charizard(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "charizard", 12, 90, new FireAction(), new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()), 1);
    }
}
