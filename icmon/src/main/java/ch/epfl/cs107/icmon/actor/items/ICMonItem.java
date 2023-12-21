package ch.epfl.cs107.icmon.actor.items;

import ch.epfl.cs107.play.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public abstract class ICMonItem extends CollectableAreaEntity {
    private final Sprite sprite;

    /** Constructor for every ICMonItem
     *
     * @param owner Area to which the Item belongs (Area)
     * @param position Position at which the item should be rendered (DiscreteCoordinates)
     * @param spriteName Name of the sprite in the resources/images/sprites/items directory (String)
     */
    public ICMonItem(Area owner, DiscreteCoordinates position, String spriteName) {
        super(owner, Orientation.DOWN, position);
        this.sprite = new RPGSprite(spriteName, 1f, 1f, this);
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        this.sprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }
}
