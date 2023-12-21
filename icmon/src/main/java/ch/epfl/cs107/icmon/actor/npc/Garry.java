package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.actor.pokemon.*;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Random;

public class Garry extends NPCActor implements ICMonFightableActor {
    private static final Random random = new Random();
    private final OrientedAnimation animation;
    private boolean defeated = false;
    private static final int ANIMATION_DURATION = 6;
    private final ArrayList<Pokemon> pokemonCollection = new ArrayList<>();

    /**
     * Constructor for the NPC Garry.
     * He intentionally has an OrientedAnimation, to make it possible for him to walk around.
     * Will always be created with the same sprite.
     * @param owner The area Garry belongs to. (Area)
     * @param orientation The orientation with which Garry should be rendered. (Orientation)
     * @param position The position, where Garry stands. (DiscreteCoordinates)
     */
    public Garry(Area owner, Orientation orientation, DiscreteCoordinates position) {
        super(owner, orientation, position, "actor/garry");
        pokemonCollection.add(new Voltball(owner, position));
        this.animation = new OrientedAnimation("actors/garry", ANIMATION_DURATION / 2, Orientation.DOWN, this);
    }

    /**
     * Gets the value of the <code>defeated</code> attribute. Means, that Garry's Pokémon is dead, when this returns
     * true.
     * @return attribute value (boolean)
     */
    public boolean isDefeated() {
        return defeated;
    }

    /**
     * Sets the value of the <code>defeated</code> attribute. Should be set to true, when Garry's Pokémon dies.
     * @param defeated new value (boolean)
     */
    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    @Override
    public void fight(ICMonFightableActor foe, ICMon.ICMonGameState state) {
        state.startSelection(this);
    }

    /**
     * Returns the first pokémon in garry's collection
     * @return (Pokemon)
     */
    public Pokemon getPokemon(){
        return pokemonCollection.get(0);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
    }
}
