package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.ShockAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Pikachu extends Pokemon{
    /**
     * Constructor for Pikachu, passes everything to super().
     * @param ownerArea Area which this Pikachu belongs to. (Area)
     * @param position Position at which this Pikachu lives. (DiscreteCoordinates)
     */
    public Pikachu(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "pikachu", 9, 80,
                new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()), 0);
        this.addFightAction(new ShockAction(this), 1);
    }
}