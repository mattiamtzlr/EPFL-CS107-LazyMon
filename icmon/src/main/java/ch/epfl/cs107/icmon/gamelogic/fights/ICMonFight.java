package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.AttackAction;
import ch.epfl.cs107.icmon.graphics.ICMonFightActionSelectionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;


public class ICMonFight extends PauseMenu {
    private enum ICMonFightState {
        INTRODUCTION,
        ACTION_SELECTION,
        ACTION_EXECUTION,
        ACTION_EXECUTION_PAUSE,
        ACTION_EXECUTION_CONTINUED,
        FOE_ACTION,
        DRAW_LAST_FRAME,
        CONCLUSION;

        private ICMonFightAction action;
        private String message;
        private ICMonFightActionSelectionGraphics selectionGraphics;

        ICMonFightState() {}
    }
    private Pokemon player;
    private Pokemon foe;
    private ICMonFightArenaGraphics arena;
    private boolean running = true;
    private ICMonFightState currentState;

    private double counter = 1f;

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
        Keyboard keyboard = getKeyboard();
        switch (this.currentState) {
            case INTRODUCTION -> {
                this.arena.setInteractionGraphics(
                        new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR,
                            String.format("A wild %s appears!    [ space-bar ]", foe.properties().name())));

                if (getKeyboard().get(Keyboard.SPACE).isPressed()) {
                    this.currentState = ICMonFightState.ACTION_SELECTION;
                    this.currentState.selectionGraphics =
                            new ICMonFightActionSelectionGraphics(CAMERA_SCALE_FACTOR, keyboard, player.getFightActions());
                }
            }

            case ACTION_SELECTION -> {
                this.arena.setInteractionGraphics(this.currentState.selectionGraphics);
                this.currentState.selectionGraphics.update(deltaTime);

                ICMonFightAction nextAction;
                if (this.currentState.selectionGraphics.choice() != null) {
                    nextAction = this.currentState.selectionGraphics.choice();

                    this.currentState.selectionGraphics =
                            new ICMonFightActionSelectionGraphics(CAMERA_SCALE_FACTOR, getKeyboard(), player.getFightActions());
                    this.currentState = ICMonFightState.ACTION_EXECUTION;
                    this.currentState.action = nextAction;
                }
            }

            case ACTION_EXECUTION -> {
                this.arena.setInteractionGraphics(
                    new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR,
                        String.format("%s uses %s!", player.properties().name(), this.currentState.action.name()))
                );

                ICMonFightAction nextAction = this.currentState.action;
                this.currentState = ICMonFightState.ACTION_EXECUTION_PAUSE;
                this.currentState.action = nextAction;
            }

            case ACTION_EXECUTION_PAUSE -> {
                if (this.counter > deltaTime)
                    this.counter -= deltaTime;
                else {
                    this.counter = 1f;
                    ICMonFightAction nextAction = this.currentState.action;
                    this.currentState = ICMonFightState.ACTION_EXECUTION_CONTINUED;
                    this.currentState.action = nextAction;
                }
            }

            case ACTION_EXECUTION_CONTINUED -> {
                boolean actionSuccess = this.currentState.action.doAction(foe);
                if (!actionSuccess) {
                    this.currentState = ICMonFightState.DRAW_LAST_FRAME;
                    this.currentState.message = "You escaped the fight!";
                }
                else if (!foe.properties().isAlive()) {
                    this.currentState = ICMonFightState.DRAW_LAST_FRAME;
                    this.currentState.message = String.format("You defeated %s! Well done!", foe.properties().name());
                }
                else {
                    this.currentState = ICMonFightState.FOE_ACTION;
                }
            }

            case FOE_ACTION -> {
                boolean actionSuccess = false;
                for (ICMonFightAction action : foe.getFightActions()){
                    if (action instanceof AttackAction) {
                        actionSuccess = action.doAction(player);
                        break; // we are aware of our sins
                    }
                }
                if (!actionSuccess) {
                    this.currentState = ICMonFightState.DRAW_LAST_FRAME;
                    this.currentState.message = String.format("%s escaped the fight!", foe.properties().name());
                }
                else if(!player.properties().isAlive()) {
                    this.currentState = ICMonFightState.DRAW_LAST_FRAME;
                    this.currentState.message = String.format("Your %s got defeated by %s. You lost :(",
                        player.properties().name(), foe.properties().name());
                }
                else {
                    this.currentState = ICMonFightState.ACTION_SELECTION;
                }

                // trust me we really are :)
            }

            case DRAW_LAST_FRAME -> {
                this.currentState = ICMonFightState.CONCLUSION;
                this.currentState.message = String.format("Your %s got defeated by %s. You lost :(",
                        player.properties().name(), foe.properties().name());
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
        super.update(deltaTime);
    }
    public boolean isRunning(){
        return running; // as long as this returns true, the game is paused
    }
}
