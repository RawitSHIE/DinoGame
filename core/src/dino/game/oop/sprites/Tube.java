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

    private Rectangle boundsTop, boundsBottom;
    private Texture topTube, bottomTube;
    private Vector2 postop, posbottom;
    private Random rand;

    public Tube(float x){
        topTube  = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        postop = new Vector2(x ,rand.nextInt(FLUCTUATION) + TUBE_GAB + LOWEST_OPENING);
        posbottom = new Vector2(x, postop.y - TUBE_GAB - bottomTube.getHeight());

        boundsTop = new Rectangle(postop.x, postop.y, topTube.getWidth(), topTube.getHeight());
        boundsBottom = new Rectangle(posbottom.x, posbottom.y, bottomTube.getWidth(), bottomTube.getHeight());
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPostop() {
        return postop;
    }

    public Vector2 getPosbottom() {
        return posbottom;
    }

    public void reposition(float x){
        postop.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAB + LOWEST_OPENING);
        posbottom.set(x, postop.y - TUBE_GAB - bottomTube.getHeight());
        boundsTop.setPosition(postop.x, postop.y);
        boundsBottom.setPosition(posbottom.x, posbottom.y);

    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBottom);

    }

    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();

    }
}
