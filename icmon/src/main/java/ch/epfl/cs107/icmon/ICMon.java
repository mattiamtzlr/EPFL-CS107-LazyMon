package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.Town;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class ICMon extends AreaGame {
    public static final float CAMERA_SCALE_FACTOR = 15.f;
    private ICMonPlayer player;
    private String[] areas = {"town"};

    private void createAreas() {
        addArea(new Town());
    }

    /**
     * ???
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        //Keyboard keyboard = getWindow().getKeyboard();
        //if (keyboard.get(Keyboard.R).isPressed()) this.begin(getWindow(), getFileSystem());
    }

    /**
     * ???
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return ???
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            createAreas();
            initArea(areas[0]);
            return true;
        }
        return false;
    }
    /**
     * ???
     */
    @Override
    public void end() {

    }



    /**
     * ???
     */
    private void switchArea() {

    }

    /**
     * ???
     * @param areaKey ???
     */
    private void initArea(String areaKey) {
        ICMonArea area = (ICMonArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new ICMonPlayer(getCurrentArea(), Orientation.DOWN, new DiscreteCoordinates(5, 5));
        player.enterArea(area, coords);
    }
    @Override
    public String getTitle() {
        return "LazyMon";
    }
}
