package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class ICMonPlayer extends ICMonActor {
    public OrientedAnimation currentAnimation;
    private final static int ANIMATION_DURATION = 8;

    public ICMonPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);
        OrientedAnimation animationLand = new OrientedAnimation("actors/player", ANIMATION_DURATION/2, orientation, this);
        OrientedAnimation animationWater = new OrientedAnimation("actors/player_water", ANIMATION_DURATION/2, orientation, this);
        // TODO This is not the way we actually want to handle animations, need to actually implement
        this.currentAnimation = animationLand;
    }
    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        moveIfPressed(Orientation.LEFT, new Button[] {keyboard.get(Keyboard.LEFT), keyboard.get(Keyboard.A)});
        moveIfPressed(Orientation.UP, new Button[] {keyboard.get(Keyboard.UP), keyboard.get(Keyboard.W)});
        moveIfPressed(Orientation.RIGHT, new Button[] {keyboard.get(Keyboard.RIGHT), keyboard.get(Keyboard.D)});
        moveIfPressed(Orientation.DOWN, new Button[] {keyboard.get(Keyboard.DOWN), keyboard.get(Keyboard.S)});
        if (isDisplacementOccurs()) currentAnimation.update(deltaTime);
        else currentAnimation.reset();
        super.update(deltaTime);
    }
    /**
     * Orientate and Move this player in the given orientation if the given button is down
     *
     * @param orientation (Orientation): given orientation, not null
     * @param b           (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, Button[] buttons) {
        for (Button b : buttons) {
            if (b.isDown()) {
              if (!isDisplacementOccurs()) {
                orientate(orientation);
                currentAnimation.orientate(orientation);
                move(ANIMATION_DURATION);
              }
            }
        }
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        currentAnimation.draw(canvas);
    }


}