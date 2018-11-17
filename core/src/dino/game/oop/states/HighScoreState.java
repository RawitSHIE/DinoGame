package dino.game.oop.states;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dino.game.oop.DinoGame;
import dino.game.oop.scoring.Score;
import dino.game.oop.sprites.Bird;

public class HighScoreState extends State{
    private static final int NUM_WIDTH = 24;
    private static final int NUM_HEIGHT = 36;

    private String[] number = {"0.png", "1.png" ,"2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png"};

    private Texture background, rank, one, ten;
    private BitmapFont ranktxt;


    public HighScoreState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, DinoGame.WIDTH,DinoGame.HEIGHT);
        background = new Texture("bg.png");
        ranktxt = new BitmapFont();
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
        for (int i = 0; i < 3; i++){
            one  = new Texture(number[Score.getScore().get(i)%10]);
            ten = new Texture(number[Score.getScore().get(i)/10]);
            rank = new Texture(number[i+1]);

            // Draw Rank
            ranktxt.setColor(Color.RED);
            ranktxt.draw(sb,
                    "RANK",
                    cam.position.x  - (NUM_WIDTH*2)*5,
                    cam.viewportHeight/2);
            sb.draw(rank ,
                    cam.position.x + 100 - (NUM_WIDTH*2 + NUM_WIDTH*2) - 2,
                    cam.viewportHeight/2 - NUM_HEIGHT*2*i + 10,
                    NUM_WIDTH*2,
                    NUM_HEIGHT*2);
            sb.draw(ten ,
                    cam.position.x + 100 - (NUM_WIDTH + NUM_WIDTH*2) - 2,
                    cam.viewportHeight/2 - NUM_HEIGHT*2*i + 10,
                    NUM_WIDTH*2,
                    NUM_HEIGHT*2);
            sb.draw(one ,
                    cam.position.x + 100 - NUM_WIDTH - 2,
                    cam.viewportHeight/2 - NUM_HEIGHT*2*i + 10,
                    NUM_WIDTH*2,
                    NUM_HEIGHT*2);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        System.out.println("EndGame State Dispose");
    }

}
