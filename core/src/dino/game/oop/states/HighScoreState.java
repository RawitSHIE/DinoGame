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

import javax.xml.soap.Text;
import java.util.ArrayList;

public class HighScoreState extends State{
    private static final int NUM_WIDTH = 24;
    private static final int NUM_HEIGHT = 36;


    private String[] number = {"0.png", "1.png" ,"2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png"};

    private Texture background, scoreboard, score, rank, one, ten;
    private Texture b1;
    private ArrayList<Texture> badge= new ArrayList<Texture>();


    public HighScoreState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, DinoGame.WIDTH,DinoGame.HEIGHT);
        background = new Texture("bg.png");
        scoreboard = new Texture("board.png");
        score = new Texture("score.png");

        badge.add(new Texture("1st.png"));
        badge.add(new Texture("2nd.png"));
        badge.add(new Texture("3rd.png"));

        b1 = new Texture("play.png");

        System.out.println(Score.getScore());
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
        sb.draw(scoreboard,
                cam.position.x - scoreboard.getWidth()/4 ,
                cam.position.y - scoreboard.getHeight()/4,
                scoreboard.getWidth()/2,
                scoreboard.getHeight()/2);

        sb.draw(score,
                cam.position.x - score.getWidth()/4,
                cam.position.y + 100,
                score.getWidth()/2,
                score.getHeight()/2);

        for (int i = 0; i < 3; i++){
            one  = new Texture(number[Score.getScore().get(i)%10]);
            ten = new Texture(number[Score.getScore().get(i)/10]);
            rank = new Texture(number[i+1]);

            // Draw Rank
            sb.draw(badge.get(i) ,
                    cam.position.x - (NUM_WIDTH*4 + 10),
                    cam.viewportHeight/2 - NUM_HEIGHT*2*i + 10,
                    NUM_WIDTH*2,
                    NUM_HEIGHT*2);

            sb.draw(rank ,
                    cam.position.x - (NUM_WIDTH*2),
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

        sb.draw(b1,
                cam.position.x - b1.getWidth()/2,
                cam.position.y - b1.getHeight()/2 - scoreboard.getHeight()/4 - 25);

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        for (Texture i : badge){
            i.dispose();
        }
        rank.dispose();
        ten.dispose();
        one.dispose();
        System.out.println("HighScoreState Dispose");
    }

}
