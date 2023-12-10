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
    private ICMonFight combat;
    private boolean running = true;

    public PokemonSelectionMenu(ArrayList<Pokemon> pokemons, Keyboard keyboard, ICMonFight combat) {
        this.graphics = new PokemonSelectionMenuGraphics(CAMERA_SCALE_FACTOR, keyboard, pokemons);
        this.combat = combat;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.graphics.choice() != null) {
            combat.setPlayer(graphics.choice());
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
}
