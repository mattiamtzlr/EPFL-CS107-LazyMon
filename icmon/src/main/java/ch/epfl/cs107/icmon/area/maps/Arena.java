package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.Door;
import ch.epfl.cs107.icmon.actor.pokemon.*;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

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
                new DiscreteCoordinates(20,15),
                this,
                new DiscreteCoordinates(4,1),
                new DiscreteCoordinates(5,1)));

        registerActor(new Enton(this, new DiscreteCoordinates(2, 6)));
        registerActor(new Gengar(this, new DiscreteCoordinates(3, 6)));
        registerActor(new Kadabra(this, new DiscreteCoordinates(5, 6)));
        registerActor(new Tentacool(this, new DiscreteCoordinates(6, 6)));
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(4,2);
    }
}
