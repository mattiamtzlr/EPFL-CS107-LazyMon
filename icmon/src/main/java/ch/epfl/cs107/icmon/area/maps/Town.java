package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.Door;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.Garry;
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

/**
 * Area for the Town, the main area of the game.
 */
public class Town extends ICMonArea {
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(20, 15);
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new Door(
                "lab",
                new DiscreteCoordinates(6, 2),
                this,
                new DiscreteCoordinates(20, 16)
        ));
        registerActor(new Door(
                "arena",
                new DiscreteCoordinates(4, 2),
                this,
                new DiscreteCoordinates(15, 24)
        ));
        registerActor(new Door(
                "house",
                new DiscreteCoordinates(3, 2),
                this,
                new DiscreteCoordinates(7, 27)
        ));
        registerActor(new Door(
                "shop",
                new DiscreteCoordinates(3,2),
                this,
                new DiscreteCoordinates(25, 20)
        ));
        registerActor(new Door(
            "route102",
            new DiscreteCoordinates(1, 12),
            this,
            new DiscreteCoordinates(29, 13),
            new DiscreteCoordinates(29, 14),
            new DiscreteCoordinates(29, 15),
            new DiscreteCoordinates(29, 16)
        ));
        registerActor(new Pikachu(this, new DiscreteCoordinates(11, 3)));
    }

    @Override
    public String getTitle() {
        return "town";
    }
}
