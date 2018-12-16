package dino.game.oop.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dino.game.oop.DinoGame;
import dino.game.oop.music.MainSong;

public class IntroState extends State {
    private Texture background;
    private Texture dino;
    private MainSong mainSong;
    int time;
    Color c;

    public IntroState(GameStateManager gsm, MainSong mainSong) {
        super(gsm);

        cam.setToOrtho(false, DinoGame.WIDTH,DinoGame.HEIGHT);

        background = new Texture("libgdx.png");
        dino = new Texture("dinoteam.png");
        cam.position.set(0,cam.position.y,0);


        this.mainSong = mainSong;
        time = 400;

    }

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
        time -= 1;
        if (time < 0){
            gsm.push(new MenuState(gsm, mainSong));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        c = sb.getColor();
        if (time >= 200){
            float t = Math.min((float) ((float) 1 - Math.abs(( Math.abs(time - 200)/100.0) - 1)) * 2, 1);
            sb.setColor(c.r, c.g, c.b, t);
            sb.draw(dino, 0,0, DinoGame.WIDTH, DinoGame.HEIGHT);

        }else if (time > 0){
            float g = Math.min((float) ((float) 1 - Math.abs(( Math.abs(time)/100.0) - 1)) * 2, 1);
            sb.setColor(c.r, c.g, c.b, g);
            sb.draw(background, 0,0, DinoGame.WIDTH, DinoGame.HEIGHT);
        }else{
            sb.setColor(c.r, c.g, c.b, 1f);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        dino.dispose();
        System.out.println("IntroState Dispose");
    }
}
