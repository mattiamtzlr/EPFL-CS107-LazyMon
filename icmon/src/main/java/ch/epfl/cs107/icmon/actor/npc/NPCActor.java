package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public abstract class NPCActor extends ICMonActor {
    private final Sprite sprite;

    /**
     * Super Constructor for all NPC-Actors
     * @param owner The area the actor belongs to. (Area)
     * @param orientation The orientation with which the actor should be rendered. (Orientation
     * @param position The position, where the actor stands. (DiscreteCoordinates)
     * @param spriteName The name of the sprite which should be used for this actor, in resources/images/sprites/actors (String)
     */
    public NPCActor(Area owner, Orientation orientation, DiscreteCoordinates position, String spriteName) {
        super(owner, orientation, position);
        this.sprite = new RPGSprite(spriteName, 1, 1.3125f, this, 
            new RegionOfInterest(0, 0, 16, 21));
    }

    @Override
    public boolean takeCellSpace() {
       return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.sprite.draw(canvas);
    }
}
