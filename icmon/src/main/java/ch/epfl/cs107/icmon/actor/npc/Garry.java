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
    private OrientedAnimation animation;
    public boolean defeated = false;
    private Random random = new Random();
    private int ANIMATION_DURATION = 6;
    private ArrayList<Pokemon> pokemonCollection = new ArrayList<>();
    public Garry(Area owner, DiscreteCoordinates position) {
        super(owner, Orientation.DOWN, position, "actor/garry");
        pokemonCollection.add(new Voltball(owner, position));
        this.animation = new OrientedAnimation("actors/garry", ANIMATION_DURATION / 2, Orientation.LEFT, this);

    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    @Override
    public void fight(ICMonFightableActor foe, ICMon.ICMonGameState state) {
        state.startSelection(this);
    }
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
