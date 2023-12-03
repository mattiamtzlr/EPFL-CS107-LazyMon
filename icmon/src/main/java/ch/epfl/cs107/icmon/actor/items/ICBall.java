package ch.epfl.cs107.icmon.actor.items;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class ICBall extends ICMonItem {
    public ICBall(Area owner, DiscreteCoordinates position, String spriteName) {
        super(owner, position, spriteName);
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }
}
