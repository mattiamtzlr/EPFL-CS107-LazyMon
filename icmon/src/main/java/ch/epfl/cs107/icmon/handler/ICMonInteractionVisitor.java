package ch.epfl.cs107.icmon.handler;

import ch.epfl.cs107.icmon.actor.Door;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;

/**
 * Contains default interaction handling methods for different actors.
 */
public interface ICMonInteractionVisitor extends AreaInteractionVisitor {
    default void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction){}
    default void interactWith(ICMonPlayer player, boolean isCellInteraction){}
    default void interactWith(ICBall ball, boolean isCellInteraction){}
    default void interactWith(ICShopAssistant assistant, boolean isCellInteraction){}
    default void interactWith(Garry garry , boolean isCellInteraction) {}
    default void interactWith(ProfOak oak, boolean isCellInteraction){}
    default void interactWith(Door door, boolean isCellInteraction) {}
    default void interactWith(Pokemon pokemon, boolean isCellInteraction) {}
}
