package ch.epfl.cs107.icmon.graphics;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;

public class PokemonSelectionMenu extends PauseMenu {
    private final PokemonSelectionMenuGraphics graphics;
    private boolean running = true;
    private int choice = -1;

    /**
     * Constructs a new pause menu which allows to select a Pokémon from the player's collection.
     * @param pokemons The Pokémon collection from which to choose. (ArrayList&lt;Pokemon&gt;)
     * @param keyboard The keyboard used for the input. (Keyboard)
     */
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

    /**
     * Returns the index of the chosen Pokémon.
     * @return Index (int)
     */
    public int choice() {
        return choice;
    }
}
