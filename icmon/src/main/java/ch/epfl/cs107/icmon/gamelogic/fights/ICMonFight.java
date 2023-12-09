package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;


public class ICMonFight extends PauseMenu {
    private enum ICMonFightState {
        INTRODUCTION,
        ACTION_SELECTION,
        ACTION_EXECUTION,
        FOE_ACTION,
        CONCLUSION;

        private ICMonFightAction action;
        private String message;

        ICMonFightState() {}

        ICMonFightState(ICMonFightAction action, String message) {
            this.action = action;
            this.message = message;
        }

        public void setAction(ICMonFightAction action) {
            this.action = action;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    private Pokemon player;
    private Pokemon foe;
    private ICMonFightArenaGraphics arena;
    private float pauseTime = 5.0f;
    private boolean running = true;
    private ICMonFightState currentState;

    public ICMonFight(Pokemon player, Pokemon foe) {
        this.player = player;
        this.foe = foe;
        this.arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, foe.properties(), player.properties());
        this.currentState = ICMonFightState.INTRODUCTION;
    }

    @Override
    protected void drawMenu(Canvas c) {
        arena.draw(c);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        switch (this.currentState) {
            case INTRODUCTION -> {
                this.arena.setInteractionGraphics(
                        new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Start by pressing space-bar"));

                if (getKeyboard().get(Keyboard.SPACE).isPressed())
                    this.currentState = ICMonFightState.ACTION_SELECTION;
            }
            case ACTION_SELECTION -> {
                ICMonFightAction nextAction = player.getFightActions().get(0);
                System.out.println("action selected: " + nextAction);

                this.currentState = ICMonFightState.ACTION_EXECUTION;
                this.currentState.action = nextAction;
            }
            case ACTION_EXECUTION -> {

                System.out.println("action execution");
                boolean actionSuccess = this.currentState.action.doAction(foe);
                if (!actionSuccess) {
                    this.currentState = ICMonFightState.CONCLUSION;
                    this.currentState.message = "you escaped";
                }
                else if (!foe.properties().isAlive()) {
                    this.currentState = ICMonFightState.CONCLUSION;
                    this.currentState.message = "you won :)";
                }
                else {

                    this.currentState = ICMonFightState.FOE_ACTION;
                }
            }
            case FOE_ACTION -> {
                System.out.println("foe turn");
                boolean actionSuccess = false;
                for (ICMonFightAction action : foe.getFightActions()){
                    if (action instanceof AttackAction) {
                        actionSuccess = action.doAction(player);
                        break; // we are aware of our sins
                    }
                }
                if (!actionSuccess) {
                    this.currentState = ICMonFightState.CONCLUSION;
                    this.currentState.message = "Foe has left, refuses to elaborate";
                }
                else if(!player.properties().isAlive()) {
                    this.currentState = ICMonFightState.CONCLUSION;
                    this.currentState.message = "you lost, L2P";
                }
                else {
                    this.currentState = ICMonFightState.ACTION_SELECTION;
                }

                // trust me we really are :)
            }
            case CONCLUSION -> {
                this.arena.setInteractionGraphics(
                        new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, this.currentState.message));

                if (getKeyboard().get(Keyboard.SPACE).isPressed()) {
                    end();
                    this.running = false;
                }
            }
        }
    }
    public boolean isRunning(){
        return running; // as long as this returns true, the game is paused
    }
}
