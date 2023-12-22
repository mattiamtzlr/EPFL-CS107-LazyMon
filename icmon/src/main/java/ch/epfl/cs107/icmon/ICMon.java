package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.pokemon.*;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.*;
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
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Behold: the main game!
 */
public class ICMon extends AreaGame {
    public static final float CAMERA_SCALE_FACTOR = 13.f;
    private final HashMap<String, Pokemon> pokedex = new HashMap<>();
    private ICMonPlayer player;
    private ICMonGameState gameState;
    private ICMonEventManager eventManager;
    private ArrayList<ICMonEvent> activeEvents;
    private ArrayList<ICMonEvent> newEvents;
    private ArrayList<ICMonEvent> completedEvents;
    private final HashMap<String, ICMonArea> areas = new HashMap<>();
    private GamePlayMessage mailbox;

    /**
     * Helper method to list all the areas which need to be created.
     */
    private void createAreas() {
        registerArea(new Town());
        registerArea(new Route102());
        registerArea(new Lab());
        registerArea(new Arena());
        registerArea(new House());
        registerArea(new Shop());
    }

    /**
     * Registers Pokémon which need to be used at some point in the Pokédex.
     */
    private void createPokemon() {
        Area currentArea = getCurrentArea();
        this.pokedex.put("bulbasaur", new Bulbasaur(currentArea, new DiscreteCoordinates(0,0)));
        this.pokedex.put("latios", new Latios(currentArea, new DiscreteCoordinates(0,0)));
        this.pokedex.put("nidoqueen", new Nidoqueen(currentArea, new DiscreteCoordinates(0,0)));
        this.pokedex.put("voltball", new Voltball(currentArea, new DiscreteCoordinates(0, 0)));
        this.pokedex.put("charizard", new Charizard(currentArea, new DiscreteCoordinates(0, 0)));
        this.pokedex.put("enton", new Enton(currentArea, new DiscreteCoordinates(0, 0)));
        this.pokedex.put("gengar", new Gengar(currentArea, new DiscreteCoordinates(0, 0)));
        this.pokedex.put("kadabra", new Kadabra(currentArea, new DiscreteCoordinates(0, 0)));
        this.pokedex.put("snorlax", new Snorlax(currentArea, new DiscreteCoordinates(0, 0)));
        this.pokedex.put("squirtle", new Squirtle(currentArea, new DiscreteCoordinates(0, 0)));
        this.pokedex.put("tentacruel", new Tentacruel(currentArea, new DiscreteCoordinates(0, 0)));
        this.pokedex.put("pikachu", new Pikachu(currentArea, new DiscreteCoordinates(0, 0)));
    }

    /**
     * Registers the given area in the HashMap and also adds it to the game.
     * @param area The area to be added. (ICMonArea)
     */
    private void registerArea(ICMonArea area) {
        this.areas.put(area.getTitle(), area);
        addArea(area);
    }

    /**
     * Updates the game state continuously
     * @param deltaTime elapsed time since last update, in seconds, non-negative. (double)
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
        // the second condition does not fully prevent softlocks, but since reset will only ever be used by devs,
        // it is allowed to come with some risks :)
        if (keyboard.get(Keyboard.R).isPressed() &&
                activeEvents.get(activeEvents.size()-1) instanceof PokemonFightEvent)
            this.begin(getWindow(), getFileSystem());
        if (keyboard.get(Keyboard.C).isPressed())
            System.out.println(player.getCurrentCells());
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


            initArea("route102");
//            initArea("house");

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

    /**
     * Handles events: All events of the game should be stated in here.
     */
    private void events(){

        Area ballArea = areas.get("lab");
        ICBall ball = new ICBall(ballArea, new DiscreteCoordinates(12, 4), "items/icball");
        CollectItemEvent collectItemEvent = new CollectItemEvent(gameState, ball, eventManager);
        collectItemEvent.onStart(new RegisterInAreaAction(ball, ballArea));

        ICMonChainedEvent chain = new ICMonChainedEvent(eventManager,
                new IntroductionEvent(eventManager, gameState),
                new FirstInteractionWithProfessorOakEvent(eventManager, gameState),
                collectItemEvent,
                new FirstInteractionWithGarryEvent(eventManager, gameState),
                new SearchForPikachuEvent(eventManager, gameState)
                );
        chain.start();
    }

    /**
     * Initialises a given area by first setting the current area to that area, then spawning the player there
     * and finally letting the player enter the area.
     * @param areaKey The identifier of the area to be initialised, as returned by its getTitle() method. (String)
     */
    private void initArea(String areaKey) {
        ICMonArea area = (ICMonArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new ICMonPlayer(getCurrentArea(), coords, this.gameState);
        player.enterArea(area, coords);
    }

    @Override
    public String getTitle() {
        return "LazyMon";
    }

    /**
     * Class which handles the communication between other elements and the game.
     */
    public class ICMonGameState {
        private ICMonGameState() {}

        /**
         * Generic interaction method
         * @param interactable The interactable to interact with. (Interactable)
         * @param isCellInteraction The type of interaction. (boolean)
         */
        public void acceptInteraction(Interactable interactable, boolean isCellInteraction) {
            for (ICMonEvent event : ICMon.this.activeEvents) {
                interactable.acceptInteraction((AreaInteractionVisitor) event, isCellInteraction);
            }
        }

        /**
         * Adds a Pokémon from the Pokédex to the player's collection.
         * @param pokemonName The name of the Pokémon to add. (String)
         */
        public void givePlayerPokemon(String pokemonName) {
            openDialog(new Dialog("received_new_pokemon"));
            player.addPokemon(pokedex.get(pokemonName));
        }

        /**
         * Returns whether the player has any Pokémon in his collection.
         * @return True if there is at least one Pokémon in the collection. (boolean)
         */
        public boolean playerHasPokemon(){
            return player.hasPokemon();
        }

        /**
         * Used to receive messages from other parts of the game.
         * @param message The message to be received by the game state. (GamePlayMessage)
         */
        public void send(GamePlayMessage message) {
            mailbox = message;
        }

        /**
         * Switches the player between the available areas
         * @param targetAreaKey The <b>name</b> of the area to switch to. (String)
         * @param targetCoords The coordinates of the player in the new area. (DiscreteCoordinates)
         */
         public void switchArea(String targetAreaKey, DiscreteCoordinates targetCoords) {
            player.leaveArea();
            setCurrentArea(targetAreaKey, false);
            player.enterArea(areas.get(targetAreaKey), targetCoords);
        }

        /**
         * Starts the Pokémon selection.
         * @param foe The current opponent. (ICMonFightableActor)
         */
        public void startSelection(ICMonFightableActor foe) {
             PokemonSelectionMenu selectionMenu = new PokemonSelectionMenu(player.getPokemons(), getCurrentArea().getKeyboard());
             PokemonSelectionEvent selectionEvent =
                     new PokemonSelectionEvent(gameState, foe, selectionMenu,  eventManager);
             send(new SuspendWithEventMessage(selectionEvent));
        }

        /**
         * Starts a Pokémon fight.
         * @param choice The index of the chosen Pokémon in the player's collection. (int)
         * @param foe The current opponent. (ICMonFightableActor)
         */
        public void startFightEvent(int choice, ICMonFightableActor foe){
             ICMonFight combat;
             if (foe instanceof Garry)
                 combat = new ICMonFight(player.getPokemons().get(choice), ((Garry) foe).getPokemon());
             else
                 combat = new ICMonFight(player.getPokemons().get(choice), (Pokemon) foe);

            PokemonFightEvent fightEvent = new PokemonFightEvent(combat, eventManager);
            fightEvent.onComplete(new LeaveAreaAction((ICMonActor) foe));
            send(new SuspendWithEventMessage(fightEvent));
        }

        /**
         * Opens a given dialog.
         * @param dialog The dialog to open and display. (Dialog)
         */
        public void openDialog(Dialog dialog) {
             player.openDialog(dialog);
        }

        /**
         * Sets the current pause menu as the given one.
         * @param menu The pause menu to be set. (PauseMenu
         */
        public void newPauseMenu(PauseMenu menu) {
            setPauseMenu(menu);

        }

        /**
         * Starts the currently assigned pause menu.
         */
        public void startPauseMenu(){
             for (ICMonEvent activeEvent : activeEvents) {
                 activeEvent.suspend();
             }
             requestPause();

        }

        /**
         * Ends the currently assigned pause menu.
         */
        public void endPauseMenu() {
            for (ICMonEvent activeEvent : activeEvents) {
                activeEvent.resume();
            }
            requestResume();
        }
    }

    /**
     * Class which handles events and everything to do with them.
     */
    public class ICMonEventManager {

        /**
         * Registers the given event in the game's events.
         * @param event The event to be registered. (ICMonEvent)
         */
        public void registerEvent(ICMonEvent event){
            newEvents.add(event);
        }

        /**
         * Removes / Unregisters the given event from the game's events.
         * @param event The event to be removed. (ICMonEvent)
         */
        public void unRegisterEvent(ICMonEvent event){
            completedEvents.add(event);
        }

    }
}
