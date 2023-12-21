package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class ICShopAssistant extends NPCActor {

    /**
     * Constructor for the NPC ICShopAssistant.
     * Will always be created with the same sprite.
     * @param owner The area the assistant belongs to. (Area)
     * @param orientation The orientation with which the assistant should be rendered. (Orientation)
     * @param position The position, where the assistant stands. (DiscreteCoordinates)
     */
    public ICShopAssistant(Area owner, Orientation orientation, DiscreteCoordinates position) {
        super(owner, orientation, position, "actors/assistant");
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
}
