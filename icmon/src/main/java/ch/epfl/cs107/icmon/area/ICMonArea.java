package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public abstract class ICMonArea extends Area {
    /**
     * In this method, every actor needed in the scene should be registered, as well as the back- and foreground.
     */
    protected abstract void createArea();

    /**
     * Returns the coordinates, at which the player should be placed, when entering this area.
     * @return The coordinates of the player. (DiscreteCoordinates)
     */
    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    /**
     * Sets the behaviour of the area, depending on its name, and calls the <code>createArea</code> method.
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return True if the game begun successfully. (boolean)
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            setBehavior(new ICMonBehavior(window, getTitle()));
            createArea();
            return true;
        }
        return false;
    }

    /**
     * Returns the scale factor of the camera, as set in ICMon.
     * @return Scale factor (float)
     */
    @Override
    public final float getCameraScaleFactor() {
        return ICMon.CAMERA_SCALE_FACTOR;
    }

}
