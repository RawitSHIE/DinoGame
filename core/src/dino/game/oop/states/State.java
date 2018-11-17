package dino.game.oop.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera cam;
//    protected OrthographicCamera normcam;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    protected State(GameStateManager gsm){
        this.gsm = gsm;
        this.cam = new OrthographicCamera();
        this.mouse = new Vector3();

    }
    protected State(GameStateManager gsm, float x, float y){
        this.gsm = gsm;
        this.cam = new OrthographicCamera(x,y);
        this.mouse = new Vector3();

    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

}
