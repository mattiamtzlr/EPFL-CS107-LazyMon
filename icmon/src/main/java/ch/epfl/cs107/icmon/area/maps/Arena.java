package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.Door;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.pokemon.*;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

/**
 * Area for the Arena.
 */
public class Arena extends ICMonArea {
    @Override
    public String getTitle() {
        return "arena";
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new Door(
                "town",
                new DiscreteCoordinates(15,23),
                this,
                new DiscreteCoordinates(4,1),
                new DiscreteCoordinates(5,1)));

        registerActor(new Garry(this, Orientation.DOWN, new DiscreteCoordinates(4, 5)));
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(4,2);
    }
}
