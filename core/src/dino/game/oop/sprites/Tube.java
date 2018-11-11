package dino.game.oop.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class Tube {
    public static final int TUBE_WIDTH = 52;

    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAB = 100;
    private static final int LOWEST_OPENING = 120;

    private Rectangle boundsTop;
    private Texture topTube;
    private Vector2 postop;
    private Random rand;

    public Tube(float x){
        topTube  = new Texture("toptube.png");
        rand = new Random();

        postop = new Vector2(x ,rand.nextInt(FLUCTUATION) + LOWEST_OPENING);
        boundsTop = new Rectangle(postop.x, postop.y, topTube.getWidth(), topTube.getHeight());
    }

    public Texture getTopTube() {
        return topTube;
    }


    public Vector2 getPostop() {
        return postop;
    }


    public void reposition(float x){
        postop.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAB + LOWEST_OPENING);
        boundsTop.setPosition(postop.x, postop.y);

    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop);

    }

    public void dispose(){
        topTube.dispose();
    }
}
