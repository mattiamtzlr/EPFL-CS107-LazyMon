package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Bulbasaur extends Pokemon{
    public Bulbasaur(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "bulbasaur", 1, 10);
    }
}
