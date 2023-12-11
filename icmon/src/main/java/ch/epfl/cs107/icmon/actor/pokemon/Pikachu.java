package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.ShockAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Pikachu extends Pokemon{
    public Pikachu(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "pikachu", 3, 20,
                new AttackAction(), new EscapeAction(), new ShockAction());
    }
}
