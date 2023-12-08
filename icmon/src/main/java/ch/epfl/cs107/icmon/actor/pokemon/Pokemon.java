package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.gamelogic.messages.SuspendWithEventMessage;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

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

    public Pokemon(Area ownerArea, DiscreteCoordinates position, String name, int dmg, int maxHp){
        super(ownerArea, Orientation.DOWN, position);
        this.name = name;
        this.damage = dmg;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.sprite = new RPGSprite("pokemon/" + name, 1, 1, this);
    }

    public void sufferDamage(int amount) {
        if (hp > amount)
            hp -= amount;
        else
            alive = false;
    }

    @Override
    public void fight(ICMonFightableActor foe, ICMon.ICMonGameState state) {
        // register combat event (PokemonFightEvent or smth.) -> EventManager
        //      => its completion should make the foe disappear -> LeaveAreaAction
        // send SuspendWithEvent message to game -> over player
        //      =>

        ICMonFight combat = new ICMonFight(this, (Pokemon) foe);
        state.startFightEvent(combat, foe);

        state.newPauseMenu(combat);
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
            return null;
        }

        public float hp(){
            return 0f;
        }

        public float maxHp(){
            return 0f;
        }

        public int damage(){
            return 0;
        }

    }

}