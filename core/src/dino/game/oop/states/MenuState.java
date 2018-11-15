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
<<<<<<< HEAD
        background = new Texture("cave-bg.png");
=======
        background = new Texture("bg.png");
>>>>>>> 90eb68801748b2c5696c06d9ff00e782bd51bf55
        playBtn = new Texture("play.png");
        cam.setToOrtho(false, DinoGame.WIDTH, DinoGame.HEIGHT);
        cam.position.set(0,cam.position.y,0);

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
        sb.begin();
        sb.draw(background, 0,0, DinoGame.WIDTH, DinoGame.HEIGHT);
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
