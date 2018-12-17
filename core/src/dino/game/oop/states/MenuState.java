package dino.game.oop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dino.game.oop.DinoGame;
import dino.game.oop.music.MainSong;

import javax.xml.soap.Text;

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    private Texture leaderBtn;
    private Texture exitBtn;
    private Texture gameLogo;

    private Texture white;
    private boolean leaderboard = false;
    private MainSong mainSong;
    private Sound c_btn;

    private float time;

    private Texture dust, backdrop;

    private String[] num_frame;

    public MenuState(GameStateManager gsm, MainSong mainSong) {
        super(gsm);

        num_frame = new String[20];

        for (int i = 0; i < 20 ; i++){
            num_frame[i] = "gif/"+Integer.toString(i+1)+".gif";
        }

        cam.setToOrtho(false, DinoGame.WIDTH,DinoGame.HEIGHT);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        leaderBtn = new Texture("leaderbutton.png");
        exitBtn = new Texture("exitbutton.png");
        gameLogo = new Texture("Logo.png");

        cam.setToOrtho(false, DinoGame.WIDTH, DinoGame.HEIGHT);
        cam.position.set(0,cam.position.y,0);
        this.mainSong = mainSong;
        c_btn = Gdx.audio.newSound(Gdx.files.internal("Sound/btn.mp3"));

        dust = new Texture("dust_fall.gif");

        time = 0;
        backdrop = new Texture("backdrop.png");

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched() && Gdx.input.getX() >= 545 && Gdx.input.getX() <= 734 && Gdx.input.getY() >= 264 && Gdx.input.getY() <= 452 && !leaderboard){
            c_btn.play();
            gsm.set(new PlayState(gsm, mainSong));
        }
        else if(Gdx.input.justTouched() && Gdx.input.getX() >= 9 && Gdx.input.getX() <= 121 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711 && !leaderboard){
            c_btn.play();
            gsm.set(new HighScoreState(gsm, mainSong));
        }
        if(Gdx.input.justTouched() && Gdx.input.getX() >= 1159 && Gdx.input.getX() <= 1272 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711){
            c_btn.play();
            System.exit(0);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        //firstpage effect

        time += 0.5;
        System.out.println(time);
        dust = new Texture(num_frame[(int) time%20]);


        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0, DinoGame.WIDTH, DinoGame.HEIGHT);

        sb.draw(dust, 0,0, 1280, 720);

        sb.draw(backdrop, 0 ,0);

        sb.draw(gameLogo, DinoGame.WIDTH / 2 - gameLogo.getWidth() / 2, DinoGame.HEIGHT / 2 + 100);

        if ( Gdx.input.getX() >= 545 && Gdx.input.getX() <= 734 && Gdx.input.getY() >= 312 && Gdx.input.getY() <= 502 && !leaderboard){
            sb.draw(playBtn, DinoGame.WIDTH / 2 - playBtn.getWidth() / 2 - 2, DinoGame.HEIGHT / 2 - 100 - 2 - 50, playBtn.getWidth()+4 , playBtn.getHeight()+4);
        }else{
            sb.draw(playBtn, DinoGame.WIDTH / 2 - playBtn.getWidth() / 2, DinoGame.HEIGHT / 2 - 100 - 50);
        }


        if (Gdx.input.getX() >= 9 && Gdx.input.getX() <= 121 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711){
            sb.draw(leaderBtn, 5 - 2, 5 - 2, leaderBtn.getWidth() + 4 ,  leaderBtn.getHeight() + 4);
        }else{
            sb.draw(leaderBtn, 5, 5);
        }

        if(Gdx.input.getX() >= 1159 && Gdx.input.getX() <= 1272 && Gdx.input.getY() >= 599 && Gdx.input.getY() <= 711) {
            sb.draw(exitBtn, DinoGame.WIDTH -125 - 2, 5 - 2 , exitBtn.getWidth() + 4, exitBtn.getHeight() + 4);
        }else{
            sb.draw(exitBtn, DinoGame.WIDTH -125, 5);
        }



        sb.end();


    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        leaderBtn.dispose();
        exitBtn.dispose();
        c_btn.dispose();
        gameLogo.dispose();
        System.out.println("MenuState Dispose");
    }
}
