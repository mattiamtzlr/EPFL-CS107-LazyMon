package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.Door;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Area for the House of the player.
 */
public class House extends ICMonArea {

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(5, 6);
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new Door(
                "town",
                new DiscreteCoordinates(7, 26),
                this,
                new DiscreteCoordinates(3, 1),
                new DiscreteCoordinates(4, 1)
        ));
    }

    @Override
    public String getTitle() {
        return "house";
    }
}
