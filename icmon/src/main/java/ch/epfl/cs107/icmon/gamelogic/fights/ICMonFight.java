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
        this.arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, player.properties(), foe.properties());
        this.currentState = ICMonFightState.INTRODUCTION;
    }

    @Override
    protected void drawMenu(Canvas c) {
        arena.draw(c);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        ICMonFightAction nextAction = null;
        String endMessage;
        switch (this.currentState) {
            case INTRODUCTION -> {
                this.arena.setInteractionGraphics(
                        new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Start by pressing space-bar"));

                if (getKeyboard().get(Keyboard.SPACE).isPressed())
                    this.currentState = ICMonFightState.ACTION_SELECTION;
            }
            case ACTION_SELECTION -> {
                nextAction = player.getFightActions().get(0);
                System.out.println("action selected: " + nextAction);
                this.currentState = ICMonFightState.ACTION_EXECUTION;
            }
            case ACTION_EXECUTION -> {

                System.out.println("action execution");
                boolean actionSuccess = nextAction.doAction(foe);
                if (!actionSuccess) {
                    endMessage = new String("you escaped");
                    this.currentState = ICMonFightState.CONCLUSION;
                }
                else if (!foe.properties().isAlive()) {
                    endMessage = "you won :)";
                    this.currentState = ICMonFightState.CONCLUSION;
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
                    endMessage = "Foe has left, refuses to elaborate";
                    this.currentState = ICMonFightState.CONCLUSION;
                }
                else if(!player.properties().isAlive()) {
                    endMessage = "you lost, L2P";
                    this.currentState = ICMonFightState.CONCLUSION;
                }
                else {
                    this.currentState = ICMonFightState.ACTION_SELECTION;
                }

                // trust me we really are :)
            }
            case CONCLUSION -> {
                System.out.println("concluding");
                this.arena.setInteractionGraphics(
                        new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, endMessage));

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
