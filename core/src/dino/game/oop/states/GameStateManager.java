package dino.game.oop.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private Stack<State> states;
    public GameStateManager() {
        states = new Stack<State>();
    }

    public void push (State state){
        this.states.push(state);
    }

    public void pop (){
        this.states.pop().dispose();
    }

    public void set(State state){
        this.states.pop().dispose();
        this.states.push(state);


    }

    public void update(float dt){
        this.states.peek().update(dt);

    }

    public void render(SpriteBatch sb){
        this.states.peek().render(sb);
    }

}
