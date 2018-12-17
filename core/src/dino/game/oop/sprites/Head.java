package dino.game.oop.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import dino.game.oop.DinoGame;

public class Head {
    private static final int GRAVITY = -15;
    private static final int  MOVEMENT = 200;

    private Rectangle bounds;
    private Vector3 position;
    private Vector3 velocity;
    private Animation birdAnimation;
    Texture texture = new Texture("FlyingBird.png");




    public Head(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);
        birdAnimation = new Animation(new TextureRegion(texture), 4,0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() /4, texture.getHeight());
    }
    public void update(float dt, float x){
        birdAnimation.update(dt);
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            int xval = Gdx.input.getX();
            int yval = Gdx.input.getY();
            position.x = x + (xval/2) - 335;
            position.y = 350 - (yval /2);
            System.out.println("Y:"+yval);
            velocity.add(dt/2, 0,0);
        }

        velocity.scl(dt);
        position.add(MOVEMENT * dt ,velocity.y, 0);
        velocity.scl(1/dt);

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            if (position.y < 61){
                position.y = 61;
            }
            if (position.y > 336 ){
                position.y = 336;
            }
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
