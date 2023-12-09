package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;


public class ICMonFight extends PauseMenu {
    private enum ICMonFightState {
        INTRODUCTION, COUNTER_HANDLING, CONCLUSION
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

        switch (this.currentState) {
            case INTRODUCTION -> {
                this.arena.setInteractionGraphics(
                        new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Start by pressing space-bar"));

                if (getKeyboard().get(Keyboard.SPACE).isPressed())
                    this.currentState = ICMonFightState.COUNTER_HANDLING;
            }
            case COUNTER_HANDLING -> {
                if (this.pauseTime <= 0)
                    this.currentState = ICMonFightState.CONCLUSION;

                this.arena.setInteractionGraphics(
                        new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, this.player.getFightActions().get(0).name()));

                this.pauseTime -= deltaTime;
                System.out.println(pauseTime);
            }
            case CONCLUSION -> {
                this.arena.setInteractionGraphics(
                        new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Good Fight! End by pressing space-bar"));

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
