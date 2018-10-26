package dino.game.oop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dino.game.oop.DinoGame;
import dino.game.oop.sprites.Bird;

public class EndGameState extends State{
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;


    private Texture background;
    private Texture gameover;
    private Bird bird;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    public EndGameState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("day.png");
        gameover = new Texture("gameover.png");

        ground = new Texture("ground.png");
        cam.setToOrtho(false, DinoGame.WIDTH / 2, DinoGame.HEIGHT / 2);
        groundPos1 = new Vector2(cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2(cam.viewportWidth/2 + ground.getWidth() , GROUND_Y_OFFSET);
        cam.position.x = 0;

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
//        updateGround();

    }

    @Override
    public void render(SpriteBatch sb) {
//        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - cam.viewportWidth/2, 0);
        sb.draw(ground ,groundPos1.x, groundPos1.y);
        sb.draw(ground ,groundPos2.x, groundPos2.y);
        sb.draw(gameover, DinoGame.WIDTH/2 - gameover.getWidth()/2, DinoGame.HEIGHT/2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameover.dispose();
        System.out.println("EndGame State Dispose");
    }

}
