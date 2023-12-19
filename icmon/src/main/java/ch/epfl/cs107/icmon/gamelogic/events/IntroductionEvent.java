package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.messages.IndependentDialogMessage;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class IntroductionEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private Dialog welcome;
    private ICMon.ICMonGameState state;
    public IntroductionEvent(ICMon.ICMonEventManager eventManager, ICMon.ICMonGameState state) {
        super(eventManager);
        this.state = state;
        this.welcome = new Dialog("welcome_to_icmon");
        this.state.openDialog(welcome);


    }

    @Override
    public void update(float deltaTime) {
        if (welcome.isCompleted())
            this.complete();
    }
}
