package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.gamelogic.actions.CompleteEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.StartEventAction;

import java.util.ArrayList;
import java.util.Arrays;

public class ICMonChainedEvent extends ICMonEvent{
    private final ArrayList<ICMonEvent> chain = new ArrayList<>();
    private final ICMonEvent initialEvent;

    public ICMonChainedEvent(ICMonEvent initialEvent, ICMonEvent... chain) {
        this.chain.addAll(Arrays.asList(chain));
        this.initialEvent = initialEvent;
        onStart(new StartEventAction(initialEvent));
        chainEvents();
    }


    private void chainEvents(){
        initialEvent.onComplete(new StartEventAction(this.chain.get(0)));
        for(int i = 1; i < chain.size(); i++){
            chain.get(i-1).onComplete(new StartEventAction(chain.get(i)));
        }
        chain.get(chain.size()-1).onComplete(new CompleteEventAction(this));
    }
}
