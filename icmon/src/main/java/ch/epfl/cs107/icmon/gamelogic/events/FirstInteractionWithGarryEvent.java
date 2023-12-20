package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;

public class FirstInteractionWithGarryEvent extends ICMonEvent implements ICMonInteractionVisitor {
    public FirstInteractionWithGarryEvent(ICMon.ICMonEventManager eventManager) {
        super(eventManager);
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        System.out.println("kill garry"); //TODO REMOVE THIS
    }

    @Override
    public void interactWith(Garry garry, boolean isCellInteraction) {
        System.out.println("interaction with garry");
    }
}
