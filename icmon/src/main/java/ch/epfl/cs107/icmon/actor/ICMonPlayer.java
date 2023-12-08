package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.gamelogic.messages.PassDoorMessage;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

public class ICMonPlayer extends ICMonActor implements Interactor {
    private OrientedAnimation currentAnimation;
    private OrientedAnimation animationLand;
    private OrientedAnimation animationWater;
    private Dialog dialog;
    private boolean inDialog;
    private final static int ANIMATION_DURATION = 6; // Handout wants 8, but we go vroom, set to 2 for maximal vroomness
    private ICMonPlayerInteractionHandler handler;
    private ICMon.ICMonGameState state;

    public ICMonPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, ICMon.ICMonGameState state) {
        super(owner, orientation, coordinates);
        this.animationLand = new OrientedAnimation("actors/player", ANIMATION_DURATION / 2, orientation, this);
        this.animationWater = new OrientedAnimation("actors/player_water", ANIMATION_DURATION / 2, orientation, this);

        this.handler = new ICMonPlayerInteractionHandler();
        this.state = state;

        setCurrentAnimation(animationLand);
    }
    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        if (inDialog) {
            if (keyboard.get(Keyboard.SPACE).isPressed())
                dialog.update(deltaTime);
            setDialogState(!dialog.isCompleted());
        }
        else {
            moveIfPressed(Orientation.LEFT, new Button[]{keyboard.get(Keyboard.LEFT), keyboard.get(Keyboard.A)});
            moveIfPressed(Orientation.UP, new Button[]{keyboard.get(Keyboard.UP), keyboard.get(Keyboard.W)});
            moveIfPressed(Orientation.RIGHT, new Button[]{keyboard.get(Keyboard.RIGHT), keyboard.get(Keyboard.D)});
            moveIfPressed(Orientation.DOWN, new Button[]{keyboard.get(Keyboard.DOWN), keyboard.get(Keyboard.S)});

            if (isDisplacementOccurs())
                currentAnimation.update(deltaTime);
            else currentAnimation.reset();
            super.update(deltaTime);
        }
    }
    /**
     * Orientate and Move this player in the given orientation if the given button is down
     *
     * @param orientation (Orientation): given orientation, not null
     * @param buttons (Button): list of buttons corresponding to the given orientation, not null
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

    public void openDialog(Dialog dialog) {
        this.dialog = dialog;
        setDialogState(true);
    }

    public void setDialogState(boolean dialogState) {
        this.inDialog = dialogState;
    }

    public void setCurrentAnimation(OrientedAnimation currentAnimation) {
        currentAnimation.orientate(this.getOrientation());
        this.currentAnimation = currentAnimation;
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return !inDialog && getOwnerArea().getKeyboard().get(Keyboard.F).isPressed();
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
        state.acceptInteraction(other, isCellInteraction);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        currentAnimation.draw(canvas);
        if (inDialog)
            dialog.draw(canvas);
    }

    private class ICMonPlayerInteractionHandler implements ICMonInteractionVisitor {
        @Override
        public void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {
            if (isCellInteraction) {
                switch (cell.getType().getWalkingType()) {
                    default -> setCurrentAnimation(animationLand);
                    case SURF -> setCurrentAnimation(animationWater);
                }
            }
        }

        @Override
        public void interactWith(ICBall ball, boolean isCellInteraction) {
            if(!isCellInteraction){
                ball.collect();
            }
        }

        @Override
        public void interactWith(Door door, boolean isCellInteraction) {
            if (isCellInteraction) {
                PassDoorMessage message = new PassDoorMessage(door);
                state.send(message);
            }
        }
    }

}