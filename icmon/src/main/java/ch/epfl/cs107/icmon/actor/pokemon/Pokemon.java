package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonSelectionEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.icmon.graphics.PokemonSelectionMenu;
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
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public abstract class Pokemon extends ICMonActor implements ICMonFightableActor {

    private String name;
    private boolean alive = true;
    private int hp;
    private int maxHp;
    private int damage;
    private RPGSprite sprite;
    private ArrayList<ICMonFightAction> fightActions = new ArrayList<>();

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

    public void sufferDamage(double amount) {
        if (hp >= amount)
            hp -= amount;
        else
            alive = false;
    }

    public ArrayList<ICMonFightAction> getFightActions() {
        return new ArrayList<>(this.fightActions);
    }

    public PokemonProperties properties() {
        return new PokemonProperties();
    }

    @Override
    public void fight(ICMonFightableActor foe, ICMon.ICMonGameState state) {
        state.startSelectionEvent(foe);
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
    }

}