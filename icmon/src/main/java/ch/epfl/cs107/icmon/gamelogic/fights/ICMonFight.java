package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;

public class ICMonFight extends PauseMenu {
    private Pokemon[] fighters;
    private float pauseTime = 3.0f;

    public ICMonFight(Pokemon p1, Pokemon p2) {
        this.fighters = new Pokemon[]{p1, p2};
    }

    @Override
    protected void drawMenu(Canvas c) {
        System.out.println("hi");
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
