package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.Town;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterInAreaAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.Game;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;

public class ICMon extends AreaGame {
    public static final float CAMERA_SCALE_FACTOR = 15.f;
    private ICMonPlayer player;
    private ICMonGameState gameState;
    private ArrayList<ICMonEvent> events;
    private String[] areas = {"town"};

    /**
     * Helper method to list all the areas which need to be created.
     */
    private void createAreas() {
        addArea(new Town());
    }

    /**
     * Updates the game state continuously
     *
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Keyboard keyboard = getWindow().getKeyboard();

        for (ICMonEvent event : events) {
            event.update(deltaTime);
        }

        // TODO: (idea) make a method which takes an action and a key and then perform that action
        //       when pressing that key.
        if (keyboard.get(Keyboard.R).isPressed())
            this.begin(getWindow(), getFileSystem());
    }

    /**
     * Starts the game
     * @param window     (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return true if the game has been successfully started, false if there has been an error
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            this.events = new ArrayList<>();
            this.gameState = new ICMonGameState();

            createAreas();

            initArea(areas[0]);

            // initialise events TODO: do this better
            ICBall ball = new ICBall(getCurrentArea(), new DiscreteCoordinates(6, 6), "items/icball");
            CollectItemEvent collectItemEvent = new CollectItemEvent(player, ball);
            collectItemEvent.onStart(new RegisterInAreaAction(ball, getCurrentArea()));
            collectItemEvent.onComplete(new LogAction("Ballin"));
            collectItemEvent.start();
            events.add(collectItemEvent);

            return true;
        }
        return false;
    }

    /**
     * Ends the game
     */
    @Override
    public void end() {
    }

    /**
     * Switches the player between the available areas
     */
    private void switchArea() {
        // TODO: Implement this
    }

    /**
     * Initialises a given area by first setting the current area to that area, then spawning the player there
     * and finally letting the player enter the area.
     *
     * @param areaKey The identifier of the area to be initialised, as returned by its getTitle() method.
     */
    private void initArea(String areaKey) {
        ICMonArea area = (ICMonArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new ICMonPlayer(getCurrentArea(), Orientation.DOWN, coords, this.gameState);
        player.enterArea(area, coords);
    }

    @Override
    public String getTitle() {
        return "LazyMon";
    }

    public class ICMonGameState {
        private ICMonGameState() {}

        public void acceptInteraction(Interactable interactable, boolean isCellInteraction) {
            for (ICMonEvent event : ICMon.this.events) {
                interactable.acceptInteraction((AreaInteractionVisitor) event, isCellInteraction);
            }
        }
    }
}
