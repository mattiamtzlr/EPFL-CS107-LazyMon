package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.ShockAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Pikachu extends Pokemon{
    public Pikachu(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "pikachu", 9, 70,
                new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()), 0);
        this.addFightAction(new ShockAction(this), 1);
    }
}