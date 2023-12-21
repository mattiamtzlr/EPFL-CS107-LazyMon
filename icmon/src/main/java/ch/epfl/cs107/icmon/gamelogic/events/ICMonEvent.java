package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.actions.Action;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnRegisterEventAction;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.engine.Updatable;

import java.util.ArrayList;

public class ICMonEvent implements Updatable {
    private boolean started = false;
    private boolean suspended = false;
    private boolean completed = false;

    private final ArrayList<Action> startActions = new ArrayList<>();
    private final ArrayList<Action> completeActions = new ArrayList<>();
    private final ArrayList<Action> suspendActions = new ArrayList<>();
    private final ArrayList<Action> resumeActions = new ArrayList<>();

    /**
     * Generic ICMonEvent.
     * @param eventManager The event manager of the game. (ICMon.ICMonEventManager)
     */
    public ICMonEvent(ICMon.ICMonEventManager eventManager) {
        onStart(new RegisterEventAction(this, eventManager));
        onComplete(new UnRegisterEventAction(this, eventManager));

        // debugging
        onStart(new LogAction("started: " + this));
        onSuspension(new LogAction("suspended: " + this));
        onResume(new LogAction("resumed: " + this));
        onComplete(new LogAction("completed: " + this));
    }

    /**
     * Returns the pause menu of this event, if one is available.
     * @return null by default, a pause menu if overridden. (null || PauseMenu)
     */
    public PauseMenu getPauseMenu() {
        return null;
    }

    /**
     * Starts the event
     */
    public final void start(){
        if (!started) {
            performActions(startActions);
            started = true;
        }
    }

    /**
     * Completes the event
     */
    public final void complete(){
        if (!completed && started) {
            performActions(completeActions);
            completed = true;
        }
    }

    /**
     * Suspends the event
     */
    public final void suspend(){
        if (!completed && !suspended && started) {
            performActions(suspendActions);
            suspended = true;
        }
    }

    /**
     * Resumes the event after suspension
     */
    public final void resume(){
        if (suspended && !completed && started) {
            performActions(resumeActions);
            suspended = false;
        }
    }

    /**
     * Adds an action to the start actions list
     * @param action The action to be added. (Action)
     */
    public final void onStart(Action action){
        startActions.add(action);
    }

    /**
     * Adds an action to the complete actions list
     * @param action The action to be added. (Action)
     */
    public final void onComplete(Action action){
        completeActions.add(action);
    }

    /**
     * Adds an action to the suspend actions list
     * @param action The action to be added. (Action)
     */
    public final void onSuspension(Action action){
        suspendActions.add(action);
    }

    /**
     * Adds an action to the resume actions list
     * @param action The action to be added. (Action)
     */
    public final void onResume(Action action){
        resumeActions.add(action);
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void update(float deltaTime) {}

    /**
     * Performs all actions in the given list.
     * @param actions The list containing the actions to perform. (Arraylist&lt;Action&gt;)
     */
    private static void performActions(ArrayList<Action> actions){
        for (Action action : actions) {
            action.perform();
        }
    }
}
