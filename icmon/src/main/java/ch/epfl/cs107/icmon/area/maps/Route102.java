package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.Door;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Route102 extends ICMonArea {
    @Override
    public String getTitle() {
        return "route102";
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new Door(
            "town",
            new DiscreteCoordinates(28, 15),
            this,
            new DiscreteCoordinates(0, 10),
            new DiscreteCoordinates(0, 11),
            new DiscreteCoordinates(0, 12)
        ));
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(1, 12);
    }
}
