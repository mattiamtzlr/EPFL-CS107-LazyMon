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
    /**
     * The different states for the finite state machine. States are also used to transfer information between states.
     */
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
    private final Pokemon player;
    private final Pokemon foe;
    private final ICMonFightArenaGraphics arena;
    private boolean running = true;
    private ICMonFightState currentState;
    private double counter = 1.5f;
    private static final Random random = new Random();

    /**
     * Utility function to reset the counter to its default value.
     */
    private void resetCounter() {
        this.counter = 1.5f;
    }

    /**
     * A pause menu, which is used to represent a fight between two Pokémon.
     * @param player The Pokémon which initiated the fight, will be drawn in the front. (Pokemon)
     * @param foe The Pokémon which is fought, will be drawn in the back, facing the player. (Pokemon)
     */
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

    /**
     * Simulates the finite state machine following this general schema:
     * <ul>
     *     <li>A short introductory text is displayed, the player presses space-bar.</li>
     *     <li>The player selects their action (if their Pokémon is not tired)</li>
     *     <li>The player executes his action after a small pause:</li>
     *     <ul>
     *         <li>If the action is an escape action, the conclusion state gets chosen and the fight is over.</li>
     *         <li>If the action results in the foe dying, the conclusion state gets chosen and the fight is over.</li>
     *         <li>Otherwise it is the turn of the foe.</li>
     *     </ul>
     *     <li>The foe choses an action and random and performs it after a small pause:</li>
     *     <ul>
     *         <li>If the action is an escape action, the conclusion state gets chosen and the fight is over.</li>
     *         <li>If the action results in the player dying, the conclusion state gets chosen and the fight is over.</li>
     *         <li>Otherwise it is again the turn of the player.</li>
     *     </ul>
     *     <li>Before the conclusion state gets evaluated, the screen is updated a last time (DRAW_LAST_FRAME).</li>
     * </ul>
     * The pauses are used to make the fight a bit more immersive.
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
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
                    if (foe.properties().escapeProbability() > 0.8 && random.nextInt(0, 11) <= 2)
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
