package ch.epfl.cs107.icmon.graphics;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;

public class PokemonSelectionMenu extends PauseMenu {
    private PokemonSelectionMenuGraphics graphics;
    private boolean running = true;
    private int choice = -1;

    public PokemonSelectionMenu(ArrayList<Pokemon> pokemons, Keyboard keyboard) {
        this.graphics = new PokemonSelectionMenuGraphics(CAMERA_SCALE_FACTOR, keyboard, pokemons);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        graphics.update(deltaTime);
        if (this.graphics.choice() >= 0) {
            this.choice = this.graphics.choice();
            end();
            this.running = false;
        }
    }

    @Override
    protected void drawMenu(Canvas c) {
        graphics.draw(c);
    }

    public boolean isRunning() {
        return this.running;
    }

    public int choice() {
        return choice;
    }
}
