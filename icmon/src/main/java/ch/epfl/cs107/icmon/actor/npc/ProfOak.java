package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class ProfOak extends NPCActor {
    
    /**
     * Constructor for the NPC Professor Oak.
     * Will always be created with the same sprite.
     * @param owner The area Professor Oak belongs to. (Area)
     * @param orientation The orientation with which Professor Oak should be rendered. (Orientation)
     * @param position The position, where Professor Oak stands. (DiscreteCoordinates)
     */
    public ProfOak(Area owner, Orientation orientation, DiscreteCoordinates position) {
        super(owner, orientation, position, "actors/prof-oak");
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
}
