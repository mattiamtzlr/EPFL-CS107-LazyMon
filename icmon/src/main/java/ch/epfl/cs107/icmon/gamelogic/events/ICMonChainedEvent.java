package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.actions.CompleteEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.StartEventAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;

import java.util.ArrayList;
import java.util.Arrays;

public class ICMonChainedEvent extends ICMonEvent implements ICMonInteractionVisitor {
    private final ArrayList<ICMonEvent> chain = new ArrayList<>();
    private final ICMonEvent initialEvent;

    /**
     * Generic chained event. Contains a collection of events of which every event starts the next event on completion.
     * TODO: Maybe use a linked list? seems fitting
     * @param eventManager The event manager of the game. (ICMon.ICMonEventManager)
     * @param initialEvent The first event in the chain. (ICMonEvent)
     * @param chain All the other events (ICMonEvent...)
     */
    public ICMonChainedEvent(ICMon.ICMonEventManager eventManager, ICMonEvent initialEvent, ICMonEvent... chain) {
        super(eventManager);
        this.chain.addAll(Arrays.asList(chain));
        this.initialEvent = initialEvent;
        onStart(new StartEventAction(initialEvent));
        chainEvents();
    }

    /**
     * Chains the events together by looping through the list and adding a StartEventAction containing the next event to every
     * event's completion-action list.
     */
    private void chainEvents(){
        initialEvent.onComplete(new StartEventAction(this.chain.get(0)));
        for(int i = 1; i < chain.size(); i++){
            chain.get(i-1).onComplete(new StartEventAction(chain.get(i)));
        }
        chain.get(chain.size()-1).onComplete(new CompleteEventAction(this));
    }
}
