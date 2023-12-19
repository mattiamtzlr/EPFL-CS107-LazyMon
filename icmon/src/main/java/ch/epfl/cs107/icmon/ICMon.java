package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.pokemon.*;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.Arena;
import ch.epfl.cs107.icmon.area.maps.House;
import ch.epfl.cs107.icmon.area.maps.Lab;
import ch.epfl.cs107.icmon.area.maps.Town;
import ch.epfl.cs107.icmon.gamelogic.actions.*;
import ch.epfl.cs107.icmon.gamelogic.events.*;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.gamelogic.messages.GamePlayMessage;
import ch.epfl.cs107.icmon.gamelogic.messages.SuspendWithEventMessage;
import ch.epfl.cs107.icmon.graphics.PokemonSelectionMenu;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.engine.actor.Dialog;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.HashMap;

public class ICMon extends AreaGame {
    public static final float CAMERA_SCALE_FACTOR = 13.f;
    private HashMap<String, Pokemon> pokedex = new HashMap<>();
    private ICMonPlayer player;
    private ICMonGameState gameState;
    private ICMonEventManager eventManager;
    private ArrayList<ICMonEvent> activeEvents;
    private ArrayList<ICMonEvent> newEvents;
    private ArrayList<ICMonEvent> completedEvents;
    // private final String[] areas = {"town"};
    private final HashMap<String, ICMonArea> areas = new HashMap<>();
    private GamePlayMessage mailbox;

    /**
     * Helper method to list all the areas which need to be created.
     */
    private void createAreas() {
        registerArea(new Town());
        registerArea(new Lab());
        registerArea(new Arena());
        registerArea(new House());
    }
    private void createPokemon() {
        this.pokedex.put("bulbasaur", new Bulbasaur(getCurrentArea(), new DiscreteCoordinates(0,0)));
        this.pokedex.put("latios", new Latios(getCurrentArea(), new DiscreteCoordinates(0,0)));
        this.pokedex.put("nidoqueen", new Nidoqueen(getCurrentArea(), new DiscreteCoordinates(0,0)));
        this.pokedex.put("voltball", new Voltball(getCurrentArea(), new DiscreteCoordinates(0, 0)));
    }
    private void registerArea(ICMonArea area) {
        this.areas.put(area.getTitle(), area);
        addArea(area);
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

        activeEvents.addAll(newEvents);
        newEvents.clear();

        activeEvents.removeAll(completedEvents);
        completedEvents.clear();

        for (ICMonEvent event : activeEvents)
            event.update(deltaTime);

        if (mailbox != null) {
            mailbox.process(gameState);
            mailbox = null;
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
            this.activeEvents = new ArrayList<>();
            this.newEvents = new ArrayList<>();
            this.completedEvents = new ArrayList<>();

            this.gameState = new ICMonGameState();
            this.eventManager = new ICMonEventManager();

            createAreas();


            initArea("house");
            createPokemon();
            events();
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

    private void events(){

        Area ballArea = areas.get("lab");
        ICBall ball = new ICBall(ballArea, new DiscreteCoordinates(12, 4), "items/icball");
        CollectItemEvent collectItemEvent = new CollectItemEvent(gameState, ball, eventManager);
        collectItemEvent.onStart(new RegisterInAreaAction(ball, ballArea));

        ICMonChainedEvent chain = new ICMonChainedEvent(eventManager,
                new IntroductionEvent(eventManager, gameState),
                new FirstInteractionWithProfessorOakEvent(eventManager, gameState),
                collectItemEvent,
                new EndOfGameEvent(gameState, eventManager)
                );
        chain.start();
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
            for (ICMonEvent event : ICMon.this.activeEvents) {
                interactable.acceptInteraction((AreaInteractionVisitor) event, isCellInteraction);
            }
        }
        public void givePlayerPokemon(String pokemonName) {
            openDialog(new Dialog("received_new_pokemon"));
            player.addPokemon(pokedex.get(pokemonName));
            player.addPokemon(pokedex.get("voltball"));
        }
        public void send(GamePlayMessage message) {
            mailbox = message;
        }

        /**
         * Switches the player between the available areas
         */
         public void switchArea(String targetAreaKey, DiscreteCoordinates targetCoords) {
            player.leaveArea();
            setCurrentArea(targetAreaKey, false);
            player.enterArea(areas.get(targetAreaKey), targetCoords);
        }

        public void startSelectionEvent(ICMonFightableActor foe) {
             PokemonSelectionMenu selectionMenu = new PokemonSelectionMenu(player.getPokemons(), getCurrentArea().getKeyboard());
             PokemonSelectionEvent selectionEvent =
                     new PokemonSelectionEvent(gameState, foe, selectionMenu,  eventManager);
             send(new SuspendWithEventMessage(selectionEvent));
        }

        public void startFightEvent(int choice, ICMonFightableActor foe){
            ICMonFight combat = new ICMonFight(player.getPokemons().get(choice), (Pokemon) foe);
            PokemonFightEvent fightEvent = new PokemonFightEvent(gameState, combat, eventManager);
            fightEvent.onComplete(new LeaveAreaAction((ICMonActor) foe));
            send(new SuspendWithEventMessage(fightEvent));
        }

        public void openDialog(Dialog dialog) {
             player.openDialog(dialog);
        }

        public void newPauseMenu(PauseMenu menu) {
            setPauseMenu(menu);

        }
        public void startPauseMenu(){
             for (ICMonEvent activeEvent : activeEvents) {
                 activeEvent.suspend();
             }
             requestPause();

        }

        public void endPauseMenu() {
            for (ICMonEvent activeEvent : activeEvents) {
                activeEvent.resume();
            }
            requestResume();
        }
    }
    public class ICMonEventManager {

        public ICMonEventManager() {
        }

        public void registerEvent(ICMonEvent event){
            newEvents.add(event);
        }
        public void unRegisterEvent(ICMonEvent event){
            completedEvents.add(event);
        }

    }
}
