package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.FireAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Charizard extends Pokemon {
    public Charizard(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "charizard", 12, 90, new FireAction(), new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()), 1);
    }
}
