package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

public class LeaveAreaAction implements Action {

    private ICMonActor actor;
    public LeaveAreaAction(ICMonActor actor) {
        this.actor = actor;
    }

    @Override
    public void perform() {
        if (actor instanceof Pokemon || ((actor instanceof Garry) && !((Garry) actor).getPokemon().properties().isAlive()))
            actor.leaveArea();
    }
}
