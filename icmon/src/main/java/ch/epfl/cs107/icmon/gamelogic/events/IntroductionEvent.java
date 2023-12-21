package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.actions.OpenDialogAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class IntroductionEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private final Dialog welcome;

    /**
     * This event is the first event in the game and welcomes the player.
     * @param eventManager The event manager of the game. (ICMon.ICMonEventManager)
     * @param state The game's state. (ICMon.ICMonGameState)
     */
    public IntroductionEvent(ICMon.ICMonEventManager eventManager, ICMon.ICMonGameState state) {
        super(eventManager);
        this.welcome = new Dialog("welcome_to_icmon");
        onStart(new OpenDialogAction(state, welcome));
        // this.state.openDialog(welcome);
    }

    @Override
    public void update(float deltaTime) {
        if (welcome.isCompleted())
            this.complete();
    }
}
