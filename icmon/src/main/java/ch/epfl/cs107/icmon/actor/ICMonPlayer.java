package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

public class ICMonPlayer extends ICMonActor implements Interactor {
    public OrientedAnimation currentAnimation;
    private final static int ANIMATION_DURATION = 6; // Handout wants 8, but we go vroom, set to 2 for maximal vroomness

    public ICMonPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);
        OrientedAnimation animationLand = new OrientedAnimation("actors/player", ANIMATION_DURATION / 2, orientation, this);
        OrientedAnimation animationWater = new OrientedAnimation("actors/player_water", ANIMATION_DURATION / 2, orientation, this);
        // TODO This is not the way we actually want to handle animations, need to actually implement
        this.currentAnimation = animationLand;
    }
    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        moveIfPressed(Orientation.LEFT, new Button[] {keyboard.get(Keyboard.LEFT), keyboard.get(Keyboard.A)});
        moveIfPressed(Orientation.UP, new Button[] {keyboard.get(Keyboard.UP), keyboard.get(Keyboard.W)});
        moveIfPressed(Orientation.RIGHT, new Button[] {keyboard.get(Keyboard.RIGHT), keyboard.get(Keyboard.D)});
        moveIfPressed(Orientation.DOWN, new Button[] {keyboard.get(Keyboard.DOWN), keyboard.get(Keyboard.S)});

        if (isDisplacementOccurs())
            currentAnimation.update(deltaTime);
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
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return getOwnerArea().getKeyboard().get(Keyboard.F).isPressed();
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        // TODO I dont know if page 11 also referes to this method ?? Did not implement it even tho I might
        // TODO should have done so :)
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        currentAnimation.draw(canvas);
    }

    private class ICMonPlayerInteractionHandler implements ICMonInteractionVisitor {
        @Override
        public void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {
            if (isCellInteraction) {
                switch (ICMonBehavior.ICMonCellType.toType(cell.hashCode())) {
                    case GRASS -> System.out.println("how do I go on from here?");
                    case WATER -> System.out.println("I do not know how to access the \"AllowedWalkingType\"");
                }
            }
        }
    }

}