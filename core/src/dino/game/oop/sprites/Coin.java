package dino.game.oop.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Coin {
    public static final int COIN_WIDTH = 32;

    private static final int FLUCTUATION = 130;
    private static final int COIN_GAB = 100;
    private static final int LOWEST_OPENING = 100;

    private Rectangle boundsCoin;
    private Texture coins;
    private Vector2 poscoins;
    private Random rand;

    public Coin(float x){
        coins  = new Texture("coin.png");
        rand = new Random();

        poscoins = new Vector2(x ,rand.nextInt(FLUCTUATION) + LOWEST_OPENING);

        boundsCoin = new Rectangle(poscoins.x, poscoins.y, coins.getWidth(), coins.getHeight());
    }

    public Texture getCoins() {
        return coins;
    }


    public Vector2 getPoscoins() {
        return poscoins;
    }


    public void reposition(float x){
        poscoins.set(x, rand.nextInt(FLUCTUATION) + COIN_GAB + LOWEST_OPENING);
        boundsCoin.setPosition(poscoins.x, poscoins.y);

    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsCoin);

    }

    public void dispose(){
        coins.dispose();
    }
}
