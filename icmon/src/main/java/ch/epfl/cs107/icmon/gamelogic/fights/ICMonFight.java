package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;

public class ICMonFight extends PauseMenu {
    private Pokemon player;
    private Pokemon foe;
    private ICMonFightArenaGraphics arena;
    private float pauseTime = 3.0f;

    public ICMonFight(Pokemon player, Pokemon foe) {
        this.player = player;
        this.foe = foe;
        this.arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, player.properties(), foe.properties());
        this.arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "UwU"));
    }

    @Override
    protected void drawMenu(Canvas c) {
        arena.draw(c);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        pauseTime -= deltaTime;
    }
    public boolean isRunning(){
        return (pauseTime > 0); // as long as this returns true, the game is paused
    }
}
