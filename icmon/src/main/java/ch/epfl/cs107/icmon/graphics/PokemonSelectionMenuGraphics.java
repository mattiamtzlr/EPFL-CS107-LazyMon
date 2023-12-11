package ch.epfl.cs107.icmon.graphics;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.engine.Updatable;
import ch.epfl.cs107.play.engine.actor.Graphics;
import ch.epfl.cs107.play.engine.actor.GraphicsEntity;
import ch.epfl.cs107.play.engine.actor.ImageGraphics;
import ch.epfl.cs107.play.engine.actor.TextGraphics;
import ch.epfl.cs107.play.io.ResourcePath;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Objects.nonNull;

public class PokemonSelectionMenuGraphics extends ICMonFightInteractionGraphics implements Updatable {
    private final ArrayList<Pokemon> pokemons;
    private static final float FONT_SIZE = .6f;
    private final Keyboard keyboard;
    private final float scaleFactor;
    private final GraphicsEntity[] selectors;
    private final Graphics header;
    private int choice = -1;
    private int currentChoice;

    public PokemonSelectionMenuGraphics(float scaleFactor, Keyboard keyboard, ArrayList<Pokemon> playerPokemons) {
        super(scaleFactor);
        this.scaleFactor = scaleFactor;
        this.keyboard = keyboard;
        this.pokemons = playerPokemons;
        selectors = new GraphicsEntity[3];
        header = new GraphicsEntity(new Vector(scaleFactor / 2f, scaleFactor / 3 - 1f),
                new TextGraphics("Please, select a pokemon", FONT_SIZE, Color.WHITE, null, 0.0f, true, false, Vector.ZERO, TextAlign.Horizontal.CENTER, TextAlign.Vertical.MIDDLE,  1f, 1003));
        this.currentChoice = 0;
    }

    @Override
    public void update(float deltaTime) {
        float scaleFactorDividend = 3f;


        // HR : Keyboard management
        if (keyboard.get(Keyboard.LEFT).isPressed()){
            currentChoice = max(0, currentChoice - 1);
        } else if (keyboard.get(Keyboard.RIGHT).isPressed())
            currentChoice = min(currentChoice + 1, pokemons.size() - 1);

        else if (keyboard.get(Keyboard.ENTER).isPressed())
            choice = currentChoice;

        // HR : Prepare the left selector
        if (currentChoice == 0){
            selectors[0] = null;
        } else {
            String spriteName = "pokemon/" + pokemons.get(currentChoice - 1).properties().name();
            var image = new ImageGraphics(ResourcePath.getSprite(spriteName), scaleFactor / scaleFactorDividend,
                    scaleFactor / scaleFactorDividend);
            image.setAlpha(.6f);
            selectors[0] = new GraphicsEntity(
                    new Vector(scaleFactor / 3 - 4.5f, scaleFactor / 2 - 1f),
                    image);
        }

        // HR : Prepare the middle selector
        String spriteName = "pokemon/" + pokemons.get(currentChoice).properties().name();
            var image = new ImageGraphics(ResourcePath.getSprite(spriteName), scaleFactor / scaleFactorDividend,
                    scaleFactor / scaleFactorDividend);
            selectors[1] = new GraphicsEntity(
                    new Vector(scaleFactor / 3 - .7f, scaleFactor / 2 - 1f),
                    image);

        // HR : Prepare the Right selector
        if (currentChoice == pokemons.size() - 1 ){
            selectors[2] = null;
        } else {
            String spriteName1 = "pokemon/" + pokemons.get(currentChoice + 1).properties().name();
            var image1 = new ImageGraphics(ResourcePath.getSprite(spriteName1), scaleFactor / scaleFactorDividend,
                    scaleFactor / scaleFactorDividend);
            image1.setAlpha(.6f);
            selectors[2] = new GraphicsEntity(
                    new Vector(scaleFactor / 3 + 3.5f, scaleFactor / 2 - 1f),
                    image1);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // HR : Draw the header
        header.draw(canvas);
        // HR : Draw the selectors that are visible (not null)
        for (var selector : selectors)
            if (nonNull(selector))
                selector.draw(canvas);
    }

    public int choice() {
        return choice;
    }
}
