package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;

public class PokemonFightEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private final ICMonFight combat;

    /**
     * This event invokes a new fight between Pokémon.
     * @param combat The fight menu to be used. (ICMonFight)
     * @param eventManager The event manager of the game. (ICMon.ICMonEventManager)
     */
    public PokemonFightEvent(ICMonFight combat, ICMon.ICMonEventManager eventManager) {
        super(eventManager);
        this.combat = combat;
    }

    @Override
    public void update(float deltaTime) {
        if (!combat.isRunning())
            complete();
    }

    @Override
    public ICMonFight getPauseMenu() {
        return combat;
    }
}
