package dino.game.oop.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Head {
    private static final int GRAVITY = 0;
    private static final int  MOVEMENT = 200;

    private Rectangle bounds;
    private Vector3 position;
    private Vector3 velocity;
    private Animation birdAnimation;
    Texture texture = new Texture("birdanimation.png");


//    private Texture bird;

    public Head(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);
//        bird = new Texture("bird.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3,0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() /3, texture.getHeight());

    }

    public void update(float dt, float x){
        birdAnimation.update(dt);
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            position.x = Gdx.input.getX() + x -650;
            position.y = 460 - Gdx.input.getY();
            velocity.add(dt/2, 0,0);
        }
        velocity.scl(dt);
        position.add(MOVEMENT * dt ,velocity.y, 0);
        velocity.scl(1/dt);
        if (position.y < 61){
            position.y = 61;
        }

        if(x - (Gdx.input.getX() + x -650) > 323){
            position.x = x - 323;
        }

        if((Gdx.input.getX() + x -650) - x > 284){
            position.x = x + 284;
        }
        if (position.y > 336){
            position.y = 336;
        }
        bounds.setPosition(position.x, position.y);
    }

    public void updateAnimation(float dt){
        birdAnimation.update(dt);
    }

    public Rectangle getBounds(){
        return bounds;
    }
    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }
    public void dispose(){
        texture.dispose();
    }
}
