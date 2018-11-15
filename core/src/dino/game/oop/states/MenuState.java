package dino.game.oop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import dino.game.oop.DinoGame;

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    private Texture leaderBtn;
    private Texture exitBtn;
    private Texture homeBtn;
    private boolean leaderboard = false;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        leaderBtn = new Texture("leaderbtn.png");
        exitBtn = new Texture("exitbtn.png");
        homeBtn = new Texture("homebtn.png");
        cam.setToOrtho(false, DinoGame.WIDTH, DinoGame.HEIGHT);
        cam.position.set(0,cam.position.y,0);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched() && Gdx.input.getX() >= 545 && Gdx.input.getX() <= 734 && Gdx.input.getY() >= 264 && Gdx.input.getY() <= 452 && !leaderboard){
            gsm.set(new PlayState(gsm));
        }
        else if(Gdx.input.justTouched() && Gdx.input.getX() >= 9 && Gdx.input.getX() <= 121 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711 && !leaderboard){
            leaderboard = true;
        }
        else if(Gdx.input.justTouched() && Gdx.input.getX() >= 9 && Gdx.input.getX() <= 121 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711 && leaderboard){
            leaderboard = false;
        }
        if(Gdx.input.justTouched() && Gdx.input.getX() >= 1159 && Gdx.input.getX() <= 1272 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711){
            System.exit(0);
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
        if(!leaderboard) {
            sb.draw(playBtn, DinoGame.WIDTH / 2 - playBtn.getWidth() / 2, DinoGame.HEIGHT / 2 - 100);
            sb.draw(leaderBtn, 5, 5);
        }
        if(leaderboard){
            sb.draw(homeBtn, 5, 5);
        }
        sb.draw(exitBtn, DinoGame.WIDTH -125, 5);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        leaderBtn.dispose();
        homeBtn.dispose();
        exitBtn.dispose();
        System.out.println("MenuState Dispose");
    }
}
