package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

public class LeaveAreaAction implements Action {

    private final ICMonActor actor;

    /**
     * Makes the given actor leave its current area: Pokémon will always leave, Garry will only leave if he has been defeated.
     * @param actor The actor to make leave. (ICMonActor)
     */
    public LeaveAreaAction(ICMonActor actor) {
        this.actor = actor;
    }

    @Override
    public void perform() {
        if (actor instanceof Garry && !((Garry) actor).getPokemon().properties().isAlive()) {
            actor.leaveArea();
            ((Garry) actor).setDefeated(true);
        } else if (actor instanceof Pokemon) {
            actor.leaveArea();

        }
    }
}
