package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Gengar extends Pokemon {
    /**
     * Constructor for Gengar, passes everything to super().
     * @param ownerArea Area which this Gengar belongs to. (Area)
     * @param position Position at which this Gengar lives. (DiscreteCoordinates)
     */
    public Gengar(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "gengar", 12, 55, new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()), 0);
        this.addFightAction(new ShadowAction(this.properties().damage()));
    }
}
