package dino.game.oop.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Potion {
    public static final int COIN_WIDTH = 32;

    private static final int FLUCTUATION = 130;
    private static final int POTION_GAB = 100;
    private static final int LOWEST_OPENING = 100;

    private Rectangle boundsPotion;
    private Texture potions;
    private Vector2 pospotions;
    private Random rand;

    public Potion(float x){
        potions = new Texture("potion.png");
        rand = new Random();
        pospotions = new Vector2(x ,rand.nextInt(FLUCTUATION) + LOWEST_OPENING);
        boundsPotion = new Rectangle(pospotions.x, pospotions.y, potions.getWidth(), potions.getHeight());
    }

    public Texture getPotions() {
        return potions;
    }

    public Vector2 getPospotions() {
        return pospotions;
    }

    public void reposition(float x){
        pospotions.set(x, rand.nextInt(FLUCTUATION) + POTION_GAB + LOWEST_OPENING);
        boundsPotion.setPosition(pospotions.x, pospotions.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsPotion);
    }

    public void dispose(){
        potions.dispose();
    }
}
