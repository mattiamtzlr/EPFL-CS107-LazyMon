package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.Action;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.engine.Updatable;

import java.util.ArrayList;

public class ICMonEvent implements Updatable {
    private boolean started = false;
    private boolean suspended = false;
    private boolean completed = false;

    private ArrayList<Action> startActions = new ArrayList<>();
    private ArrayList<Action> completeActions = new ArrayList<>();
    private ArrayList<Action> suspendActions = new ArrayList<>();
    private ArrayList<Action> resumeActions = new ArrayList<>();

    public ICMonEvent() {
        onSuspension(new LogAction("suspended: " + this));
        onResume(new LogAction("resumed: " + this));
    }

    public PauseMenu getPauseMenu() {
        return null;
    }

    public final void start(){
        if (!started) {
            performActions(startActions);
            started = true;
        }
    }
    public final void complete(){
        if (!completed && started) {
            performActions(completeActions);
            completed = true;
        }
    }
    public final void suspend(){
        if (!completed && !suspended && started) {
            performActions(suspendActions);
            suspended = true;
        }
    }
    public final void resume(){
        if (suspended && !completed && started) {
            performActions(resumeActions);
            suspended = false;
        }
    }
    public final void onStart(Action action){
        startActions.add(action);
    }
    public final void onComplete(Action action){
        completeActions.add(action);
    }
    public final void onSuspension(Action action){
        suspendActions.add(action);
    }
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

    private static void performActions(ArrayList<Action> actions){
        for (Action action : actions) {
            action.perform();
        }
    }
}
