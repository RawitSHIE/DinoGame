package dino.game.oop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dino.game.oop.DinoGame;
import dino.game.oop.scoring.Score;
import dino.game.oop.sprites.*;
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
    private Texture bg, ground, gameover;
    private Texture score_one, score_ten;
    private Texture bar, bgh;
    private Texture board;
    private Texture one, ten, rank;

    private Array<Coin> coins;
    private Array<Obstacle> obstacles;
    private Potion potion;

    private Random rand;

    private int score = 0;
    private double health = 100;
    private boolean ishighscore = false;
    private boolean set = false;

    private BitmapFont ranktxt;
    private BitmapFont highscore;

    private String[] number = {"0.png", "1.png" ,"2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png"};

    private Texture playbtn, menubtn;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, DinoGame.WIDTH/2 , DinoGame.HEIGHT/2);
        bg = new Texture("bg-play.png");
        rand = new Random();
        ground = new Texture("new-ground.png");
        bird = new Bird(20,ground.getHeight() + GROUND_Y_OFFSET);
        head = new Head(20,20);
        bgh = new Texture("white-dot.png");
        board = new Texture("White-dot.png");

        ranktxt = new BitmapFont();
        highscore = new BitmapFont();
        playbtn  = new Texture("play.png");
        menubtn = new Texture("menu.png");

        //  grounds
        groundPos1 = new Vector2(cam.position.x/10 - cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x/10 - cam.viewportWidth/2) + ground.getWidth(), GROUND_Y_OFFSET);
        gameover = new Texture("gameover.png");

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

        // bar
        bar = new Texture("dot.png");
        potion = new Potion((POTION_SPACING + Coin.COIN_WIDTH) + (OBS_SPACING + 2 * Obstacle.OBS_WIDTH)/2 - Coin.COIN_WIDTH/2);
        System.out.println(Score.getScore());
        collide = false;
        score = 0;
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() && !collide) {
//            System.out.println("Touch");

        }else if(Gdx.input.justTouched()) {
            gsm.set(new MenuState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        if (!collide){
            bird.update(dt);
            cam.position.x = bird.getPosition().x + 80;
            head.update(dt, cam.position.x);

            for (Obstacle obstacle : obstacles){
                if (cam.position.x - (cam.viewportWidth/2) > obstacle.getPostop().x + obstacle.getTopobs().getWidth()){
                    obstacle.reposition(obstacle.getPostop().x + ((Obstacle.OBS_WIDTH + OBS_SPACING) * 4));
                }
                if(obstacle.collides(head.getBounds())){
                    collide = true;
                    System.out.println(score);
                }
            }

            for (Coin c : coins){
                if (cam.position.x - (cam.viewportWidth/2) > c.getPoscoins().x + c.getCoins().getWidth()){
                    c.reposition(c.getPoscoins().x + ((c.getCoins().getHeight() + COINS_SPACING) * COINS_COUNT));
                }
                if (c.collides(head.getBounds())){
                    c.reposition(c.getPoscoins().x + ((c.getCoins().getHeight() + COINS_SPACING) * COINS_COUNT));
                    score ++;
                }
            }

            if (cam.position.x - (cam.viewportWidth/2) > potion.getPospotions().x + potion.getPotions().getWidth()){
                potion.reposition(potion.getPospotions().x + ((potion.getPotions().getHeight() + POTION_SPACING)));
            }
            if (potion.collides(head.getBounds())){
                potion.reposition(potion.getPospotions().x + ((potion.getPotions().getHeight() + POTION_SPACING)));
                if (health + 10 >= 100){
                    health = 99;
                }else{
                    health += 20;
                }
            }

            health = health - 0.1;
            if (health <=  0){
                collide = true;
            }

            cam.update();

        }else{
            bird.updateAnimation(dt);
            head.updateAnimation(dt);
        }


    }

    @Override
    public void render(SpriteBatch sb) {
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

        //draw ground
        sb.draw(ground ,groundPos1.x, groundPos1.y);
        sb.draw(ground ,groundPos2.x, groundPos2.y);

        //game over
        if (collide){
            if (!set){
                ishighscore = Score.setScore(score);
                set = true;
            }

            // bg-ground
            sb.draw(board,
                    cam.position.x-100,
                    cam.position.y-75,
                    200,
                    150);

            sb.draw(gameover,
                    cam.position.x - gameover.getWidth()/2,
                    cam.viewportHeight/2 + 10);

            for (Integer i = 0; i < 3; i++){
                one  = new Texture(number[Score.getScore().get(i)%10]);
                ten = new Texture(number[Score.getScore().get(i)/10]);
                rank = new Texture(number[i+1]);

                // Draw Score
                if (ishighscore){
                    highscore.setColor(Color.RED);
                    highscore.draw(sb,
                        "Highscore!!",
                        cam.position.x  - 70,
                        cam.viewportHeight/2);
                }

                int indexone = score % 10;
                int indexten = (int) Math.floor(score/10);

                score_one = new Texture(number[indexone]);
                score_ten = new Texture(number[indexten]);

                sb.draw(score_one,
                        cam.position.x - 60 + NUM_WIDTH ,
                        cam.viewportHeight/2 - gameover.getHeight() - 10);
                if (indexten != 0){
                    sb.draw(score_ten,
                            cam.position.x - 60,
                            cam.viewportHeight/2 - gameover.getHeight() - 10);
                }

                // Draw Rank
                ranktxt.setColor(Color.RED);
                ranktxt.draw(sb,
                        "RANK",
                        cam.position.x + 100 - (NUM_WIDTH/2)*5,
                        cam.viewportHeight/2);
                sb.draw(rank ,
                        cam.position.x + 100 - (NUM_WIDTH*2 + NUM_WIDTH/2) - 2,
                        cam.viewportHeight/2 - gameover.getHeight() - NUM_HEIGHT/2*i + 10,
                        NUM_WIDTH/2,
                        NUM_HEIGHT/2);
                sb.draw(ten ,
                        cam.position.x + 100 - (NUM_WIDTH + NUM_WIDTH/2) - 2,
                        cam.viewportHeight/2 - gameover.getHeight() - NUM_HEIGHT/2*i + 10,
                        NUM_WIDTH/2,
                        NUM_HEIGHT/2);
                sb.draw(one ,
                        cam.position.x + 100 - NUM_WIDTH - 2,
                        cam.viewportHeight/2-gameover.getHeight() - NUM_HEIGHT/2*i + 10,
                        NUM_WIDTH/2,
                        NUM_HEIGHT/2);
            }

            // endgame btn
            sb.draw(playbtn,
                    cam.position.x - playbtn.getWidth()/2 - (menubtn.getWidth()/4 - 20) ,
                    cam.position.y - playbtn.getHeight()/2 - 80,
                    playbtn.getWidth()/2,
                    playbtn.getHeight()/2);

            sb.draw(menubtn,
                    cam.position.x +  menubtn.getWidth()/2 - (menubtn.getWidth()/4 + 20) ,
                    cam.position.y - menubtn.getHeight()/2 - 80,
                    menubtn.getWidth()/2,
                    menubtn.getHeight()/2);

        }

        //score screen

        int indexone = score % 10;
        int indexten = (int) Math.floor(score/10);

        score_one = new Texture(number[indexone]);
        score_ten = new Texture(number[indexten]);
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
        sb.draw(potion.getPotions(),
                potion.getPospotions().x,
                potion.getPospotions().y);
        sb.end();
    }

    //for delete old one
    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        gameover.dispose();
        score_one.dispose();
        score_ten.dispose();
        board.dispose();
        bar.dispose();
        bgh.dispose();
        one.dispose();
        ten.dispose();
        rank.dispose();
        potion.dispose();
        playbtn.dispose();
        menubtn.dispose();

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
