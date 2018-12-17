package dino.game.oop.states;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dino.game.oop.DinoGame;
import dino.game.oop.extra.Score;
import dino.game.oop.music.MainSong;

import java.util.ArrayList;

public class HighScoreState extends State{
    private static final int NUM_WIDTH = 24;
    private static final int NUM_HEIGHT = 36;


    private String[] number = {"0.png", "1.png" ,"2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png"};

    private Texture background, scoreboard, score, rank, one, ten;
    private ArrayList<Texture> badge= new ArrayList<Texture>();
    private Texture homeBtn, exitBtn;

    private MainSong mainSong;
    private Sound c_btn;


    public HighScoreState(GameStateManager gsm, MainSong mainSong) {
        super(gsm);
        cam.setToOrtho(false, DinoGame.WIDTH,DinoGame.HEIGHT);
        background = new Texture("bg.png");
        scoreboard = new Texture("board.png");
        score = new Texture("score.png");

        homeBtn = new Texture("homebtn.png");
        exitBtn = new Texture("exitbutton.png");

        badge.add(new Texture("1st.png"));
        badge.add(new Texture("2nd.png"));
        badge.add(new Texture("3rd.png"));

        System.out.println(Score.getScore());
        this.mainSong = mainSong;

        c_btn = Gdx.audio.newSound(Gdx.files.internal("Sound/btn.mp3"));
        one  = new Texture(number[0]);
        ten = new Texture(number[0]);
        rank = new Texture(number[0]);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched() && Gdx.input.getX() >= 1159 && Gdx.input.getX() <= 1272 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711){
            System.exit(0);
        }
        else if(Gdx.input.justTouched() && Gdx.input.getX() >= 9 && Gdx.input.getX() <= 121 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711){
            c_btn.play();
            gsm.set(new MenuState(gsm, mainSong));
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
        one.dispose();
        ten.dispose();
        rank.dispose();

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

        if(Gdx.input.getX() >= 9 && Gdx.input.getX() <= 121 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711) {
            sb.draw(homeBtn, 5 - 2, 5 - 2, homeBtn.getWidth() + 4, homeBtn.getHeight() + 4);
        }else{
            sb.draw(homeBtn, 5, 5);
        }

        if(Gdx.input.getX() >= 1159 && Gdx.input.getX() <= 1272 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711) {
            sb.draw(exitBtn, DinoGame.WIDTH -125 - 2, 5-2 , exitBtn.getWidth() + 4 , exitBtn.getHeight() + 4);
        }else{
            sb.draw(exitBtn, DinoGame.WIDTH -125, 5);
        }

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
        homeBtn.dispose();
        exitBtn.dispose();

        System.out.println("HighScoreState Dispose");
    }

}
