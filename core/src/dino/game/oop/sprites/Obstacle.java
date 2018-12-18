package dino.game.oop.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Obstacle {
    public static final int OBS_WIDTH = 52;

    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAB = 100;
    private static final int LOWEST_OPENING = 120;

    private Rectangle boundsTop, boundsbottom;
    private Texture topobs, bottomobs;
    private Vector2 postop, posbottom;
    private Random rand;

    public Obstacle(float x){
        topobs = new Texture("toprock.png");
        bottomobs = new Texture("bottomrock.png");

        rand = new Random();

        postop = new Vector2(x ,rand.nextInt(FLUCTUATION) + TUBE_GAB + LOWEST_OPENING);
        posbottom = new Vector2(x ,postop.y - TUBE_GAB - bottomobs.getHeight());

        boundsTop = new Rectangle(postop.x, postop.y, topobs.getWidth(), topobs.getHeight());
        boundsbottom = new Rectangle(posbottom.x, posbottom.y, bottomobs.getWidth(), bottomobs.getHeight());
    }

    public Texture getTopobs() {
        return topobs;
    }

    public Texture getBottomobs(){
        return bottomobs;
    }


    public Vector2 getPostop() {
        return postop;
    }

    public Vector2 getPosbottom() {
        return posbottom;
    }

    public void reposition(float x){
        postop.set(x, rand.nextInt(FLUCTUATION + TUBE_GAB) + LOWEST_OPENING);
        posbottom.set(x, postop.y - TUBE_GAB - bottomobs.getHeight());
        boundsTop.setPosition(postop.x, postop.y);
        boundsbottom.setPosition(posbottom.x, posbottom.y);

    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsbottom);

    }

    public void dispose(){
        topobs.dispose();
    }
}
