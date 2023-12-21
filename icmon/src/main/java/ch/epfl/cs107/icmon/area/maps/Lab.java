package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.Door;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

/**
 * Area for the Lab of Professor Oak.
 */
public class Lab extends ICMonArea {
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(6, 2);
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new ProfOak(this, Orientation.DOWN, new DiscreteCoordinates(11, 7)));
        registerActor(new Door(
                "town",
                new DiscreteCoordinates(20,  15),
                this,
                new DiscreteCoordinates(6, 1),
                new DiscreteCoordinates(7, 1)
        ));
    }

    @Override
    public String getTitle() {
        return "lab";
    }
}
