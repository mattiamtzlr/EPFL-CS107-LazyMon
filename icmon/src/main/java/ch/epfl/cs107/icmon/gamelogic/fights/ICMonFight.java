package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.actions.EscapeAction;
import ch.epfl.cs107.icmon.graphics.ICMonFightActionSelectionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Random;


public class ICMonFight extends PauseMenu {
    private enum ICMonFightState {
        INTRODUCTION,
        ACTION_SELECTION,
        ACTION_EXECUTION,
        ACTION_EXECUTION_PAUSE,
        ACTION_EXECUTION_CONTINUED,
        FOE_ACTION,
        FOE_ACTION_CONTINUED,
        DRAW_LAST_FRAME,
        CONCLUSION;

        private ICMonFightAction action;
        private String message;
        private ICMonFightActionSelectionGraphics selectionGraphics;
        private String pauseCondition;

        ICMonFightState() {}
    }
    private Pokemon player;
    private Pokemon foe;
    private ICMonFightArenaGraphics arena;
    private boolean running = true;
    private ICMonFightState currentState;
    private double counter = 1.5f;
    private Random random = new Random();

    private void resetCounter() {
        this.counter = 1.5f;
    }

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
                if (player.properties().isOnFire())
                    player.sufferDamage(2);

                if (player.properties().isTired()) {
                    this.arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR,
                        String.format("%s is too tired to fight this round!", player.properties().name())));
                    player.setTired(false);
                    this.currentState = ICMonFightState.ACTION_EXECUTION_PAUSE;
                    this.currentState.pauseCondition = "tired_player";

                } else {
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
            }

            case ACTION_EXECUTION -> {
                this.arena.setInteractionGraphics(
                    new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR,
                        String.format("%s uses %s!", player.properties().name(), this.currentState.action.name()))
                );

                ICMonFightAction nextAction = this.currentState.action;
                this.currentState = ICMonFightState.ACTION_EXECUTION_PAUSE;
                this.currentState.pauseCondition = "player";
                this.currentState.action = nextAction;
            }

            case ACTION_EXECUTION_PAUSE -> {
                if (this.counter > deltaTime)
                    this.counter -= deltaTime;
                else {
                    resetCounter();
                    ICMonFightAction nextAction = this.currentState.action;

                    switch (this.currentState.pauseCondition) {
                        case "player" -> this.currentState = ICMonFightState.ACTION_EXECUTION_CONTINUED;
                        case "foe" -> this.currentState = ICMonFightState.FOE_ACTION_CONTINUED;
                        case "tired_player" -> this.currentState = ICMonFightState.FOE_ACTION;
                        case "tired_foe" -> this.currentState = ICMonFightState.ACTION_SELECTION;
                    }
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
                if (foe.properties().isTired()) {
                    this.arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR,
                        String.format("%s is too tired to fight this round!", foe.properties().name())));
                    foe.setTired(false);
                    this.currentState = ICMonFightState.ACTION_EXECUTION_PAUSE;
                    this.currentState.pauseCondition = "tired_foe";
                } else {
                    if (foe.properties().isOnFire())
                        foe.sufferDamage(2);

                    ICMonFightAction nextAction = null;
                    // if escapeProbability is great enough and in one out of 4 times the foe will flee.
                    if (foe.properties().escapeProbability() > 0.7 && random.nextInt(0, 11) <= 2)
                        nextAction = new EscapeAction(); // every pokémon has an escape action
                    else {
                        while (nextAction == null) {
                            ICMonFightAction randomAction =
                                foe.getFightActions().get(random.nextInt(0, foe.getFightActions().size()));
                            nextAction = randomAction instanceof EscapeAction ? null : randomAction;
                        }
                    }

                    this.arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR,
                        String.format("%s uses %s", foe.properties().name(), nextAction.name())));
                    this.currentState = ICMonFightState.ACTION_EXECUTION_PAUSE;
                    this.currentState.pauseCondition = "foe";
                    this.currentState.action = nextAction;
                }
            }
            case FOE_ACTION_CONTINUED -> {
                boolean actionSuccess = this.currentState.action.doAction(player);

                if (!actionSuccess) {
                    this.currentState = ICMonFightState.DRAW_LAST_FRAME;
                    this.currentState.message = String.format("%s escaped the fight!", foe.properties().name());
                } else if (!player.properties().isAlive()) {
                    this.currentState = ICMonFightState.DRAW_LAST_FRAME;
                    this.currentState.message = String.format("Your %s got defeated by %s. You lost :(",
                        player.properties().name(), foe.properties().name());
                } else {
                    this.currentState = ICMonFightState.ACTION_SELECTION;
                }
            }

            case DRAW_LAST_FRAME -> {
                String message = this.currentState.message;
                this.currentState = ICMonFightState.CONCLUSION;
                this.currentState.message = message;
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
