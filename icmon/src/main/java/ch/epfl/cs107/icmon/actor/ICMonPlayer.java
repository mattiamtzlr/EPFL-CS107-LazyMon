package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.pokemon.*;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The main character of the game.
 */
public class ICMonPlayer extends ICMonActor implements Interactor {
    private OrientedAnimation currentAnimation;
    private final OrientedAnimation animationLand;
    private final OrientedAnimation animationWater;
    private Dialog dialog;
    private boolean inDialog;
    private boolean surf;
    private final static int ANIMATION_DURATION = 6;
    private final ICMonPlayerInteractionHandler handler;
    private final ICMon.ICMonGameState state;
    private final ArrayList<Pokemon> pokemonCollection = new ArrayList<>();
    private static long lastWildPokmonInteraction = 0;
    private static final Random random = new Random();

    /**
     * The constructor for the player, the main character. Two animations are initialised, one for movement on land and one
     * for movement on / in water. The player gets initialised looking downwards, to look "at" the person who is playing.
     * @param ownerArea The area that the player belongs to. (Area)
     * @param coordinates The coordinates where the player is. (DiscreteCoordinates)
     * @param state The game state to be used for this player. (ICMon.ICMonGameState)
     */
    public ICMonPlayer(Area ownerArea, DiscreteCoordinates coordinates, ICMon.ICMonGameState state) {
        super(ownerArea, Orientation.DOWN, coordinates);
        this.animationLand = new OrientedAnimation("actors/player", ANIMATION_DURATION / 2, Orientation.DOWN, this);
        this.animationWater = new OrientedAnimation("actors/player_water", ANIMATION_DURATION / 2, Orientation.DOWN, this);

        this.handler = new ICMonPlayerInteractionHandler();
        this.state = state;

        /*========================================================================================
         #    The following lines can be uncommented, to test all Pokémon and their features.    #
         #    The uncommented Pokémon get added to the player's Pokémon collection.              #
         ========================================================================================*/

//        addPokemon(new Bulbasaur(getOwnerArea(), getCurrentMainCellCoordinates()));
//        addPokemon(new Nidoqueen(getOwnerArea(), getCurrentMainCellCoordinates()));
        addPokemon(new Pikachu(getOwnerArea(), getCurrentMainCellCoordinates()));
//        addPokemon(new Latios(getOwnerArea(), getCurrentMainCellCoordinates()));
//        addPokemon(new Voltball(getOwnerArea(), getCurrentMainCellCoordinates()));
//        addPokemon(new Gengar(getOwnerArea(), getCurrentMainCellCoordinates()));
        addPokemon(new Tentacruel(getOwnerArea(), getCurrentMainCellCoordinates()));
//        addPokemon(new Enton(getOwnerArea(), getCurrentMainCellCoordinates()));
//        addPokemon(new Kadabra(getOwnerArea(), getCurrentMainCellCoordinates()));
        addPokemon(new Charizard(getOwnerArea(), getCurrentMainCellCoordinates()));
//        addPokemon(new Squirtle(getOwnerArea(), getCurrentMainCellCoordinates()));
//        addPokemon(new Snorlax(getOwnerArea(), getCurrentMainCellCoordinates()));

        setCurrentAnimation(animationLand);
    }

    @Override
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
     * @param orientation The new orientation (Orientation)
     * @param buttons The list of buttons corresponding to the given orientation. (Button[])
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

    /**
     * Returns, whether the player has any Pokémon in its collection. Note that this method doesn't check any properties of
     * the Pokémon.
     * @return True if there is at least one Pokémon in the player's collection. (boolean)
     */
    public boolean hasPokemon(){
        return !pokemonCollection.isEmpty();
    }

    /**
     * Allows the preparation of player for a dialog. Sets the <code>inDialog</code> attribute to true and the
     * <code>dialog</code> attribute to the given dialog.
     * @param dialog The dialog to be used. (Dialog)
     */
    public void openDialog(Dialog dialog) {
        this.dialog = dialog;
        setDialogState(true);
    }

    /**
     * Sets the <code>inDialog</code> attribute to the given value.
     * @param dialogState The new value. (boolean)
     */
    public void setDialogState(boolean dialogState) {
        this.inDialog = dialogState;
    }

    /**
     * Adds a Pokémon to the player's collection.
     * @param pokemon The Pokémon to be added. (Pokemon)
     */
    public void addPokemon(Pokemon pokemon) {
        this.pokemonCollection.add(pokemon);
        if (pokemon instanceof Voltball) {
            setSurf();
        }
    }

    /**
     * Returns a defensive copy of the player's Pokémon collection, to be used in Pokémon selection.
     * @return Copy of the player's Pokémon collection. (Arraylist&lt;Pokemon&gt;)
     */
    public ArrayList<Pokemon> getPokemons() {
        return new ArrayList<>(this.pokemonCollection);
    }

    /**
     * Sets the orientation of the provided animation to the current orientation of the player (to avoid visual glitches)
     * and then sets that animation as the current one.
     * @param currentAnimation The new animation. (OrientedAnimation)
     */
    private void setCurrentAnimation(OrientedAnimation currentAnimation) {
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

    /**
     * Sets the player's <code>surf</code> attribute to true.
     */
    private void setSurf() {
        this.surf = true;
    }

    /**
     * Returns if the player has the ability to swim - "surf" in the original games - which currently only depends on if
     * they have a Voltball Pokémon in their collection.
     * @return True if the player has a the surf ability. (boolean)
     */
    public boolean hasSurf() {
        return this.surf;
    }

    @Override
    public void draw(Canvas canvas) {
        currentAnimation.draw(canvas);
        if (inDialog)
            dialog.draw(canvas);
    }

    /**
     * Class which handles interactions between the player and other actors / cells.
     * Implements the interactWith method from the ICMonInteractionVisitor interface.
     */
    private class ICMonPlayerInteractionHandler implements ICMonInteractionVisitor {
        @Override
        public void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {
            if (isCellInteraction) {
                switch (cell.getType().getWalkingType()) {
                    case SURF -> setCurrentAnimation(animationWater);
                    case FEET -> {
                        setCurrentAnimation(animationLand);
                        if (cell.getType().equals(ICMonBehavior.ICMonCellType.GRASS)) {
                            long currentMillis = System.currentTimeMillis();
                            if (
                                random.nextInt(15) < 1  // one in 15 chance
                                    && (currentMillis - lastWildPokmonInteraction) > 7000   // wait at least 7 secs
                            ) {
                                state.startWildPokemonFight();
                                lastWildPokmonInteraction = System.currentTimeMillis();
                            }
                        }
                    }
                    case ALL -> setCurrentAnimation(animationLand);
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
                if (!(door.getDestinationArea().equals("route102") && pokemonCollection.isEmpty())) {
                    PassDoorMessage message = new PassDoorMessage(door);
                    state.send(message);
                } else if (!isDisplacementOccurs()) {
                    openDialog(new Dialog("cannot_enter_route102"));
                    orientate(getOrientation().opposite());
                    move(ANIMATION_DURATION);
                }
            }
        }

        @Override
        public void interactWith(Pokemon pokemon, boolean isCellInteraction) {
            if (isCellInteraction) {
                if (!pokemonCollection.isEmpty())
                    pokemonCollection.get(0).fight(pokemon, state);
                else if (!isDisplacementOccurs()) {
                    openDialog(new Dialog("no_pokemon"));
                    orientate(getOrientation().opposite());
                    move(ANIMATION_DURATION);
                }
            }
        }
    }

}