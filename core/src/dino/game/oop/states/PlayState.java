package dino.game.oop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dino.game.oop.DinoGame;
import dino.game.oop.extra.Score;
import dino.game.oop.music.MainSong;
import dino.game.oop.sprites.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayState extends State {
    private static final int OBS_SPACING = 125;
    private static final int OBS_COUNT = 4;
    private static final int COINS_COUNT = 6;
    private static final int COINS_SPACING = (OBS_SPACING + (Obstacle.OBS_WIDTH - Coin.COIN_WIDTH))*1;
    private static final int POTION_SPACING = (OBS_SPACING + Obstacle.OBS_WIDTH) * 5 - Coin.COIN_WIDTH;
    private static final int GROUND_Y_OFFSET = -50;
    private static final int NUM_WIDTH = 24;
    private static final int NUM_HEIGHT = 36;

    private Bird bird;
    private Head head;
    private Vector2 groundPos1, groundPos2;
    private boolean collide;
    private Texture bg, ground, blank;
    private Texture score_one, score_ten;
    private Texture bar, bgh;
    private Texture board, high;
    private Texture one, ten, rank;
    private Texture playb, menu;

    private Texture dust, white, expo;

    private Texture bloodframe, bloodbg;

    private Array<Coin> coins;
    private Array<Obstacle> obstacles;
    private Potion potion;

    private Random rand;

    private int score = 0;
    private double health = 100;
    private boolean ishighscore = false;
    private boolean set = false;
    private boolean isplay = false;

    private String[] number = {"0.png", "1.png" ,"2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png"};
    private ArrayList<Texture> badge = new ArrayList<Texture>();

    private Music haweii;
    private Sound c_sound, heart, c_btn, c_hit, c_highscore;

    private MainSong mainSong;
    private int time = 300;
    private int count = 0;
    private String[] num_frame, num_flash, num_expo;


    public PlayState(GameStateManager gsm, MainSong mainSong) {
        super(gsm);
        this.mainSong = mainSong;

        num_frame = new String[20];
        num_flash = new String[20];
        num_expo = new String[30];
        for (int i = 0; i < 20 ; i++){
            num_frame[i] = "gif/"+Integer.toString(i+1)+".gif";
            num_flash[i] = "flash/"+Integer.toString(i+1)+".png";
        }
        for (int j = 0; j < 90 ; j += 3){
            num_expo[j/3] = "expo/"+Integer.toString(j+1)+".png";
        }

        mainSong.pause();
        cam.setToOrtho(false, DinoGame.WIDTH/2 , DinoGame.HEIGHT/2);
        bg = new Texture("bg-play.png");
        rand = new Random();
        ground = new Texture("new-ground.png");
        bird = new Bird(20,ground.getHeight() + GROUND_Y_OFFSET);
        head = new Head(20,150);

        bgh = new Texture("white-dot.png");
        board = new Texture("menu-b.png");
        high = new Texture("menu-highscore.png");

        badge.add(new Texture("1st.png"));
        badge.add(new Texture("2nd.png"));
        badge.add(new Texture("3rd.png"));

        playb = new Texture("playbutton.png");
        menu = new Texture("homebtn.png");
        blank = new Texture("FlyingBird.png");

        white = new Texture("white-dot.png");

        score_one = new Texture(number[0]);
        score_ten = new Texture(number[0]);

        //  grounds
        groundPos1 = new Vector2(cam.position.x/10 - cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x/10 - cam.viewportWidth/2) + ground.getWidth(), GROUND_Y_OFFSET);

        //  Obs Collection
        obstacles = new Array<Obstacle>();
        for (int i = 1; i <= OBS_COUNT; i++){
            obstacles.add(new Obstacle( i * (OBS_SPACING + Obstacle.OBS_WIDTH)));
        }

        //  Coins Collection
        coins = new Array<Coin>();
        for (int i = 1 ; i <= COINS_COUNT; i++){
            coins.add(new Coin( i * (COINS_SPACING + Coin.COIN_WIDTH) + (OBS_SPACING + 2 * Obstacle.OBS_WIDTH)/2 - Coin.COIN_WIDTH/2));
        }

//      blood
        bloodbg = new Texture("bg-blood.png");
        bloodframe = new Texture("blood-frame.png");

        // bar
        bar = new Texture("dot.png");
        potion = new Potion((POTION_SPACING + Coin.COIN_WIDTH) + (OBS_SPACING + 2 * Obstacle.OBS_WIDTH)/2 - Coin.COIN_WIDTH/2);
        System.out.println(Score.getScore());
        collide = false;
        score = 0;

        haweii = Gdx.audio.newMusic(Gdx.files.internal("Sound/Main_1.mp3"));
        haweii.setLooping(true);
        haweii.setVolume(0.2f);
        haweii.play();

        c_sound = Gdx.audio.newSound(Gdx.files.internal("Sound/coin.wav"));
        heart = Gdx.audio.newSound(Gdx.files.internal("Sound/heart.wav"));
        c_btn = Gdx.audio.newSound(Gdx.files.internal("Sound/btn.mp3"));
        c_hit = Gdx.audio.newSound(Gdx.files.internal("Sound/hit.mp3"));
        c_highscore = Gdx.audio.newSound(Gdx.files.internal("Sound/highscore.mp3"));
        white = new Texture(num_flash[0]);
        dust = new Texture(num_frame[0]);
        expo = new Texture(num_expo[0]);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() && !collide) {

        }else if(Gdx.input.justTouched() && Gdx.input.getX() >= 662 && Gdx.input.getX() <= 796 && Gdx.input.getY() >= 562 && Gdx.input.getY() <= 695) {
            c_btn.play();
            mainSong.play();
            gsm.set(new MenuState(gsm, mainSong));

        }else if(Gdx.input.justTouched() && Gdx.input.getX() >= 485 && Gdx.input.getX() <= 617 && Gdx.input.getY() >= 562 && Gdx.input.getY() <= 695) {
            c_btn.play();
            gsm.set(new PlayState(gsm, mainSong));
        }
    }

    @Override
    public void update(float dt) {
        time -= 1;

        handleInput();
        if (!collide) {
            if (time < 0){
                bird.update(dt);
                updateGround();
                head.update(dt, cam.position.x);

                System.out.println(time);
            }else{
            }
            cam.position.x = bird.getPosition().x + 80;

            for (Obstacle obstacle : obstacles) {
                if (cam.position.x - (cam.viewportWidth / 2) > obstacle.getPostop().x + obstacle.getTopobs().getWidth()) {
                    obstacle.reposition(obstacle.getPostop().x + ((Obstacle.OBS_WIDTH + OBS_SPACING) * 4));
                }
                if (obstacle.collides(head.getBounds())) {
                    c_hit.play();
                    collide = true;
                    System.out.println(score);
                }
            }

            for (Coin c : coins) {
                if (cam.position.x - (cam.viewportWidth / 2) > c.getPoscoins().x + c.getCoins().getWidth()) {
                    c.reposition(c.getPoscoins().x + ((c.getCoins().getHeight() + COINS_SPACING) * COINS_COUNT));
                }
                if (c.collides(head.getBounds())) {
                    c.reposition(c.getPoscoins().x + ((c.getCoins().getHeight() + COINS_SPACING) * COINS_COUNT));
                    c_sound.play(0.9f);
                    score++;
                    int indexone = score % 10;
                    int indexten = (int) Math.floor(score/10);

                    score_one.dispose();
                    score_ten.dispose();

                    score_one = new Texture(number[indexone]);
                    score_ten = new Texture(number[indexten]);

                }
            }

            if (cam.position.x - (cam.viewportWidth / 2) > potion.getPospotions().x + potion.getPotions().getWidth()) {
                potion.reposition(potion.getPospotions().x + ((potion.getPotions().getHeight() + POTION_SPACING)));
            }

            if (potion.collides(head.getBounds())) {
                potion.reposition(potion.getPospotions().x + ((potion.getPotions().getHeight() + POTION_SPACING)));
                heart.play();
                if (health + 10 >= 100) {
                    health = 99;
                } else {
                    health += 20;
                }
            }

            if (time < 0)
                health = health - 0.1;
            if (health <= 0) {
                collide = true;
            }
            cam.update();
        } else {
            bird.updateAnimation(dt);
            head.updateAnimation(dt);

            if (ishighscore && !(isplay)){
                c_highscore.play(0.2f);
                isplay = true;
            }
        }

    }

    @Override
    public void render(SpriteBatch sb) {

        count ++;

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - cam.viewportWidth/2, 0);
        sb.draw(bg, cam.position.x - (cam.viewportWidth/2) + bg.getWidth(), 0);
        sb.draw(bg, cam.position.x - (cam.viewportWidth/2) + bg.getWidth()*2, 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        sb.draw(head.getTexture(), head.getPosition().x, head.getPosition().y);



        for (Obstacle obstacle : obstacles){
            sb.draw(obstacle.getTopobs(), obstacle.getPostop().x , obstacle.getPostop().y);
            sb.draw(obstacle.getBottomobs(), obstacle.getPosbottom().x, obstacle.getPosbottom().y);
        }

        for (Coin c : coins){
            sb.draw(c.getCoins(), c.getPoscoins().x, c.getPoscoins().y);
        }

        sb.draw(potion.getPotions(), potion.getPospotions().x, potion.getPospotions().y);

//        dust stage
        white.dispose();
        dust.dispose();
        expo.dispose();

        white = new Texture(num_flash[count%20]);
        dust = new Texture(num_frame[count%20]);
        expo = new Texture(num_expo[count%30]);

        if (count%2000 >= 500){
            if ((count%2000 >= 500 && count%2000 <= 520) || (count%2000 >= 980 && count%2000 <= 1000)){
                sb.draw(white, cam.position.x - cam.viewportWidth/2,
                        cam.position.y-cam.viewportHeight/2,
                        cam.viewportWidth,
                        cam.viewportHeight);
            }

            if (count%2000 > 510 && count%2000 < 990 ){
                sb.draw(dust,
                        cam.position.x - cam.viewportWidth/2,
                        cam.position.y-cam.viewportHeight/2,
                        cam.viewportWidth,
                        cam.viewportHeight);
            }

        }

        if (count%2000 >= 1500){
            if ((count%2000 >= 1500 && count%2000 <= 1520) || (count%2000 >= 1980 && count%2000 <= 2000)){
                sb.draw(white, cam.position.x - cam.viewportWidth/2,
                        cam.position.y-cam.viewportHeight/2,
                        cam.viewportWidth,
                        cam.viewportHeight);
            }

            if (count%2000 > 1510 && count%1000 < 1990 ){
                sb.draw(expo,
                        cam.position.x - cam.viewportWidth/2,
                        cam.position.y-cam.viewportHeight/2,
                        cam.viewportWidth,
                        cam.viewportHeight);
            }

        }




        //countdown
        int tt = Math.max(time/60 , 0);

        sb.draw(new Texture(number[tt%10]), cam.viewportWidth/8, cam.viewportHeight/2);
        if (time < 200)
            sb.draw(new Texture("tur1.png"), cam.viewportWidth/8 - 230 ,cam.viewportHeight/2 + 50 );
        if (time < 100)
            sb.draw(new Texture("tur2.png"), cam.viewportWidth/8 - 230 ,cam.viewportHeight/2 - 70 );

        //draw ground
        sb.draw(ground ,groundPos1.x, groundPos1.y);
        sb.draw(ground ,groundPos2.x, groundPos2.y);

        //game over
        if (collide){
            //debug
            if (!set){
                ishighscore = Score.setScore(score);
                set = true;
            }
            if (ishighscore){

                sb.draw(high,
                        cam.position.x - board.getWidth()/8,
                        cam.position.y - board.getHeight()/10,
                        board.getWidth()/4,
                        board.getHeight()/4);

            }else{
                sb.draw(board,
                        cam.position.x - board.getWidth()/8,
                        cam.position.y - board.getHeight()/10,
                        board.getWidth()/4,
                        board.getHeight()/4);
            }

            for (Integer i = 0; i < 3; i++){
                one  = new Texture(number[Score.getScore().get(i)%10]);
                ten = new Texture(number[Score.getScore().get(i)/10]);
                rank = new Texture(number[i+1]);

                // Draw Score
                int indexone = score % 10;
                int indexten = (int) Math.floor(score/10);

                score_one = new Texture(number[indexone]);
                score_ten = new Texture(number[indexten]);

                sb.draw(score_one,
                        cam.position.x - 60 + NUM_WIDTH ,
                        cam.viewportHeight/2 - 10);
                sb.draw(score_ten,
                        cam.position.x - 60,
                        cam.viewportHeight/2 - 10);

                // Draw Rank
                sb.draw(badge.get(i),
                        cam.position.x + 10,
                        cam.viewportHeight/2  - NUM_HEIGHT/2*i + 10,
                        NUM_WIDTH/2,
                        NUM_HEIGHT/2);

                sb.draw(rank ,
                        cam.position.x + 95 - (NUM_WIDTH*2 + NUM_WIDTH/2) - 2,
                        cam.viewportHeight/2  - NUM_HEIGHT/2*i + 10,
                        NUM_WIDTH/2,
                        NUM_HEIGHT/2);
                sb.draw(ten ,
                        cam.position.x + 95 - (NUM_WIDTH + NUM_WIDTH/2) - 2,
                        cam.viewportHeight/2  - NUM_HEIGHT/2*i + 10,
                        NUM_WIDTH/2,
                        NUM_HEIGHT/2);
                sb.draw(one ,
                        cam.position.x + 95 - NUM_WIDTH - 2,
                        cam.viewportHeight/2 - NUM_HEIGHT/2*i + 10,
                        NUM_WIDTH/2,
                        NUM_HEIGHT/2);
            }

            // endgame btn

            float adj_width = cam.position.x - cam.viewportWidth/2 ;

            if(Gdx.input.getX() >= 485 && Gdx.input.getX() <= 617 && Gdx.input.getY() >= 562 && Gdx.input.getY() <= 695) {
                sb.draw(playb, adj_width + 240 -2, 10 - 2, 70 + 4, 70 + 4);
            }else{
                sb.draw(playb, adj_width + 240, 10, 70, 70);
            }

            if(Gdx.input.getX() >= 662 && Gdx.input.getX() <= 796 && Gdx.input.getY() >= 562 && Gdx.input.getY() <= 695) {
                sb.draw(menu, adj_width + cam.viewportWidth - menu.getWidth()/2 - 250 - 2, 10 - 2 , 70 + 4 , 70 + 4);
            }else {
                sb.draw(menu, adj_width + cam.viewportWidth - menu.getWidth()/2 - 250, 10,70 , 70);
            }

            if(Gdx.input.justTouched()){
                System.out.println("X="+Gdx.input.getX());
                System.out.println("Y="+Gdx.input.getY());
                System.out.println("---------------------");
            }
        }

        //score screen

        int indexten = (int) Math.floor(score/10);

        sb.draw(score_one,
                cam.position.x + 200 ,
                cam.viewportHeight - 50);
        if (indexten != 0){
            sb.draw(score_ten,
                    cam.position.x + 200  - score_ten.getWidth() - 5 ,
                    cam.viewportHeight - 50);
        }

        // health Progress bar
        float ratio = (float) health/100;

        sb.draw(bgh,
                cam.position.x - cam.viewportWidth/2 + 19,
                cam.position.y - cam.viewportHeight/4 ,
                12,
                cam.viewportHeight/2);
        sb.draw(bar,
                cam.position.x - cam.viewportWidth/2 + 20,
                cam.position.y - cam.viewportHeight/4 ,
                10,
                cam.viewportHeight/2 * ratio );
        sb.draw(bloodframe,
                cam.position.x - cam.viewportWidth/2 + 20 - 3,
                cam.position.y - cam.viewportHeight/4 - 10,
                bloodframe.getWidth()/4 ,
                cam.viewportHeight/2 + 20);

        sb.end();
    }

    //for delete old one
    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        score_one.dispose();
        score_ten.dispose();
        board.dispose();
        bar.dispose();
        bgh.dispose();
        one.dispose();
        ten.dispose();
        rank.dispose();
        potion.dispose();

        board.dispose();
        high.dispose();

        bloodframe.dispose();
        bloodbg.dispose();

        menu.dispose();
        playb.dispose();
        blank.dispose();

        haweii.dispose();
        c_highscore.dispose();
        c_btn.dispose();
        c_hit.dispose();

        for (Texture i : badge){
            i.dispose();
        }

        for (Obstacle obstacle : obstacles){
            obstacle.dispose();
        }
        for (Coin c : coins){
            c.dispose();
        }

        System.out.println("PlayState Dispose");
    }

    //for Update ground over and over
    public void updateGround(){
        if (cam.position.x - cam.viewportWidth/2 > groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth()*2, 0);
        }
        if (cam.position.x - cam.viewportWidth/2 > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth()*2, 0);
        }
    }


}