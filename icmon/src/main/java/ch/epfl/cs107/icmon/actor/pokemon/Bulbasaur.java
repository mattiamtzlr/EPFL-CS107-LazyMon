package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;

public class Bulbasaur extends Pokemon{
    public Bulbasaur(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "bulbasaur", 7, 60,
            new EscapeAction());

        this.addFightAction(new AttackAction(this.properties().damage()), 0);
    }
}
