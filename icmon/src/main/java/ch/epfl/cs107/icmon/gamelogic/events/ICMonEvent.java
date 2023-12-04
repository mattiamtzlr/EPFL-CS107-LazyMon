package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.gamelogic.actions.Action;
import ch.epfl.cs107.play.engine.Updatable;

import java.util.ArrayList;

public class ICMonEvent implements Updatable {
    private boolean isStarted = false;
    private boolean isSuspended = false;
    private boolean isCompleted = false;

    private ArrayList<Action> startActions = new ArrayList<>();
    private ArrayList<Action> completeActions = new ArrayList<>();
    private ArrayList<Action> suspendActions = new ArrayList<>();
    private ArrayList<Action> resumeActions = new ArrayList<>();

    void start(){};
    void complete(){};
    void suspend(){};
    void resume(){};
    void onStart(){};
    void onComplete(){};
    void onSuspension(){};
    void onResume(){};
    @Override
    public void update(float deltaTime) {

    }
}
