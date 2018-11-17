package dino.game.oop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dino.game.oop.DinoGame;

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, DinoGame.WIDTH,DinoGame.HEIGHT);
        background = new Texture("bg.png");
        playBtn = new Texture("play.png");
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
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - cam.viewportWidth/2,0, DinoGame.WIDTH, DinoGame.HEIGHT);
        sb.draw(playBtn, DinoGame.WIDTH / 2 - playBtn.getWidth()/2, DinoGame.HEIGHT/2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("MenuState Dispose");
    }
}
