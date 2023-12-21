package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.Actor;

public class RegisterInAreaAction implements Action {
    private final Actor actor;
    private final Area area;

    /**
     * Registers a given actor (including items) in a given area.
     * @param actor The actor to register. (Actor)
     * @param area The area, where the actor is registered. (Area)
     */
    public RegisterInAreaAction(Actor actor, Area area) {
        this.actor = actor;
        this.area = area;
    }

    @Override
    public void perform() {
        area.registerActor(actor);
    }
}
