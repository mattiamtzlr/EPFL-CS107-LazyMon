package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.Door;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.pokemon.Bulbasaur;
import ch.epfl.cs107.icmon.actor.pokemon.Pikachu;
import ch.epfl.cs107.icmon.actor.pokemon.Voltball;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class Town extends ICMonArea {
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(20, 15);
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new ICShopAssistant(this, Orientation.DOWN, new DiscreteCoordinates(10, 11)));
        registerActor(new Door(
                "lab",
                new DiscreteCoordinates(6, 2),
                this,
                new DiscreteCoordinates(15, 24)
        ));
        registerActor(new Door(
                "arena",
                new DiscreteCoordinates(4, 2),
                this,
                new DiscreteCoordinates(20, 16)
        ));
        registerActor(new Door(
                "house",
                new DiscreteCoordinates(2, 2),
                this,
                new DiscreteCoordinates(7, 27)
        ));

        registerActor(new Pikachu(this, new DiscreteCoordinates(11, 3)));
        registerActor(new Voltball(this, new DiscreteCoordinates(23, 23)));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public String getTitle() {
        return "town";
    }
}
