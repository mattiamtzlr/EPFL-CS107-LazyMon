package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract class, which all pokémon inherit from.
 */
public abstract class Pokemon extends ICMonActor implements ICMonFightableActor {
    private final String name;
    private boolean alive = true;
    private boolean onFire = false;
    private int thornRounds = 0;
    private boolean tired = false;
    private int hp;
    private final int maxHp;
    private final int damage;
    private final RPGSprite sprite;
    private final ArrayList<ICMonFightAction> fightActions = new ArrayList<>();

    /**
     * Super constructor for a generic Pokémon,
     * @param ownerArea The area, the Pokémon belongs to. (Area)
     * @param position The position, at which the Pokémon is rendered. (DiscreteCoordinates)
     * @param name The name of the Pokémon. (String)
     * @param dmg The amount of damage, the Pokémon inflicts with its default "Tackle" attack. (int)
     * @param maxHp The maximal amount of health of the Pokémon. (int)
     * @param fightActions The fight action(s) this Pokémon can perform. (ICMonFightAction...)
     */
    public Pokemon(Area ownerArea, DiscreteCoordinates position, String name, int dmg, int maxHp,
                   ICMonFightAction ... fightActions) {
        super(ownerArea, Orientation.DOWN, position);
        this.name = name;
        this.damage = dmg;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.sprite = new RPGSprite("pokemon/" + name, 1, 1, this);
        this.fightActions.addAll(Arrays.asList(fightActions));

    }

    /**
     * Method to add a fight action to a Pokémon, which is needed to add actions, which take properties of the Pokémon as
     * parameters (like its damage), as these are only available after construction.
     * @param fightAction The action to be added. (ICMonFightAction)
     */
    protected void addFightAction(ICMonFightAction fightAction) {
        this.fightActions.add(fightAction);
    }

    /**
     * Method to add a fight action to a Pokémon, which is needed to add actions, which take properties of the Pokémon as
     * parameters (like its damage), as these are only available after construction.
     * The action is added at the provided index, which is important, as the order of the ArrayList defines the order of
     * actions in the selection menu during a fight.
     * @param fightAction The action to be added. (ICMonFightAction)
     * @param index The index at which the action should be added. (int)
     */
    protected void addFightAction(ICMonFightAction fightAction, int index) {
        this.fightActions.add(index, fightAction);
    }

    @Override
    public boolean takeCellSpace() {
        return super.takeCellSpace();
    }

    /**
     * Lets the Pokémon take damage, gets usually called by opponent's actions.
     * @param amount The amount of damage to inflict. (int)
     */
    public void sufferDamage(int amount) {
        if (hp >= amount)
            hp -= amount;
        else {
            this.alive = false;
            this.hp = 0;
        }
    }

    /**
     * Allows the Pokémon to regenerate some health points, checks that the amount does not lead to an increase greater than
     * the defined maximal health of the Pokémon. Usually gets called by one of the Pokémon's actions.
     * @param amount The amount to heal. (int)
     */
    public void heal(int amount) {
        this.hp = Math.min(this.hp + amount, this.maxHp);
    }

    /**
     * Allows this Pokémon to be set on fire, which then causes it to take damage every following round.
     */
    public void setOnFire() {
        this.onFire = true;
    }
    public void setThorned(int rounds){
        this.thornRounds = rounds;
    }

    /**
     * Sets the <code>tired</code> attribute of the Pokémon to the provided value, which causes the Pokémon to skip the next
     * round of fighting.
     * @param tired The new value. (boolean)
     */
    public void setTired(boolean tired) {
        this.tired = tired;
    }

    /**
     * Returns a defensive copy of all fight actions of this Pokémon, to be used in the fight.
     * @return Copy of the fight actions. (ArrayList&lt;ICMonFightAction&gt;)
     */
    public ArrayList<ICMonFightAction> getFightActions() {
        return new ArrayList<>(this.fightActions);
    }

    /**
     * Returns a new PokemonProperties object for this Pokémon, containing getters to all its important attributes such as:
     * <ul>
     *     <li>name</li> <code>.name()</code>
     *     <li>health points</li> <code>.hp()</code>
     *     <li>maximal health points</li> <code>.maxHp()</code>
     *     <li>damage value</li> <code>.damage()</code>
     *     <li>whether it is alive</li> <code>.isAlive()</code>
     *     <li>whether it is on fire</li> <code>.isOnFire()</code>
     *     <li>whether it is tired</li> <code>.isTired()</code>
     *     <li>how likely it is that the Pokémon will flee.</li> <code>.escapeProbability()</code>
     * </ul>
     * @return PokemonProperties object which can be used to get the desired properties. (PokemonProperties)
     */
    public PokemonProperties properties() {
        return new PokemonProperties();
    }

    @Override
    public void fight(ICMonFightableActor foe, ICMon.ICMonGameState state) {
        state.startSelection(foe);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return super.getCurrentCells();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    /**
     * Class which contains getters to all important properties of a Pokémon.
     * @author Hamza REMMAL (hamza.remmal@epfl.ch)
     */
    public final class PokemonProperties {

        public String name(){
            return Pokemon.this.name;
        }

        public float hp(){
            return Pokemon.this.hp;
        }

        public float maxHp(){
            return Pokemon.this.maxHp;
        }

        public int damage(){
            return Pokemon.this.damage;
        }
        public boolean isAlive() {
            return Pokemon.this.alive;
        }
        public boolean isOnFire() {
            return Pokemon.this.onFire;
        }

        public boolean isTired() {
            return Pokemon.this.tired;
        }
        public boolean isThorned() {
            Pokemon.this.thornRounds -= 1;
            return thornRounds >= 1;
        }

        /**
         * Calculates, how likely an enemy Pokémon is to escape the fight, by taking the complement of its hp divided
         * by its max-hp.
         * @return percentage between 0 and 1 inclusive.
         */
        public double escapeProbability() {
            return 1 - this.hp() / this.maxHp();
        }
    }
}