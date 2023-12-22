package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.VineAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;

public class Bulbasaur extends Pokemon{
    /**
     * Constructor for Bulbasaur, passes everything to super().
     * @param ownerArea Area which this Bulbasaur belongs to. (Area)
     * @param position Position at which this Bulbasaur lives. (DiscreteCoordinates)
     */
    public Bulbasaur(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "bulbasaur", 12, 60,
            new EscapeAction());

        this.addFightAction(new AttackAction(this.properties().damage()), 0);
        this.addFightAction(new VineAction(this.properties().damage()), 1);
    }
}
