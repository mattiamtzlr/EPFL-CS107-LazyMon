package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.play.areagame.area.Area;
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
public abstract class Pokemon extends ICMonActor {

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