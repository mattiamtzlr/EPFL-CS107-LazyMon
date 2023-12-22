package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.ConfuseAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Tentacruel extends Pokemon {
    /**
     * Constructor for Tentacruel, passes everything to super().
     * @param ownerArea Area which this Tentacruel belongs to. (Area)
     * @param position Position at which this Tentacruel lives. (DiscreteCoordinates)
     */
    public Tentacruel(Area ownerArea, DiscreteCoordinates position) {
        super(ownerArea, position, "tentacruel", 8, 65, new EscapeAction());
        this.addFightAction(new AttackAction(this.properties().damage()));
        this.addFightAction(new ConfuseAction(), 1);
    }
}
