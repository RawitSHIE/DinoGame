package dino.game.oop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dino.game.oop.DinoGame;
import dino.game.oop.sprites.Bird;
import dino.game.oop.sprites.Coin;
import dino.game.oop.sprites.Head;
import dino.game.oop.sprites.Tube;

import java.util.Random;

public class PlayState extends State{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int COINS_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    private boolean fall;

    private Bird bird;
    private Head head;
    private Texture bg;
//    private Tube tube;
    private Texture ground;
    private Vector2 groundPos1, groundPos2, groundPos3, groundPos4;
    private boolean collide;
    private Texture gameover;
    private Array<Coin> coins;

    private Array<Tube> tubes;

    private Random rand;
    private boolean drag = false;

    double scale = 0;
    private int flag = 0;
    private boolean isjump;
    private int score = 0;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, DinoGame.WIDTH /2, DinoGame.HEIGHT/2 );
        bg = new Texture("day.png");
        rand = new Random();
        ground = new Texture("ground.png");
        bird = new Bird(20,ground.getHeight() + GROUND_Y_OFFSET);
        head = new Head(20,20);
//        grounds
        groundPos1 = new Vector2(cam.position.x/10 - cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x/10 - cam.viewportWidth/2) + ground.getWidth(), GROUND_Y_OFFSET);
        groundPos3 = new Vector2((cam.position.x/10 - cam.viewportWidth/2) + ground.getWidth()*2, GROUND_Y_OFFSET);
        groundPos4 = new Vector2((cam.position.x/10 - cam.viewportWidth/2) + ground.getWidth()*3, GROUND_Y_OFFSET);

        gameover = new Texture("gameover.png");

        tubes = new Array<Tube>();
        for (int i = 1 ; i <= TUBE_COUNT; i++){
            tubes.add(new Tube( i * (TUBE_SPACING+ Tube.TUBE_WIDTH)));
        }

        coins = new Array<Coin>();
        for (int i = 1 ; i <= COINS_COUNT; i++){
            coins.add(new Coin( i * (TUBE_SPACING + Coin.COIN_WIDTH)));
        }

        collide = false;
        scale = 0;

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() && !collide){
            bird.jump();
            bird.setFall(true);
            System.out.println("Touch");

        }else if(Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
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

            for (Tube tube : tubes){

                if (cam.position.x - (cam.viewportWidth/2) > tube.getPostop().x + tube.getTopTube().getWidth()){
                    tube.reposition(tube.getPostop().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * 4));
//                    flag++;

                }

                if(tube.collides(head.getBounds())){
//                    gsm.set(new EndGameState(gsm));
                    collide = true;
                    System.out.println("Gameover");
                }

            }

            for (Coin c : coins){
                if (cam.position.x - (cam.viewportWidth/2) > c.getPoscoins().x + c.getCoins().getWidth()){
                    c.reposition(c.getPoscoins().x + ((c.getCoins().getHeight() + TUBE_SPACING)  * COINS_COUNT));
                }

                if (c.collides(head.getBounds())){
                    c.reposition(c.getPoscoins().x + ((c.getCoins().getHeight() + TUBE_SPACING)  * COINS_COUNT));
//                    System.out.println("point +1");
                    score ++;
                }

            }

//            score tubes

//            int tmp = flag % 4;
//            if (tubes.get(tmp).getPostop().x + Tube.TUBE_WIDTH/2 + 0.5 + bird.getBounds().getWidth()/2 <= bird.getPosition().x
//                    &&
//                    tubes.get(tmp).getPostop().x + Tube.TUBE_WIDTH/2 + 3 + bird.getBounds().getWidth()/2>= bird.getPosition().x){
//
//                score ++;
//                System.out.println(score);
//            }

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

        for (Tube tube : tubes){
            sb.draw(tube.getTopTube(), tube.getPostop().x , tube.getPostop().y);
        }

        for (Coin c : coins){
            sb.draw(c.getCoins(), c.getPoscoins().x, c.getPoscoins().y);
        }

        //first draw ground
        sb.draw(ground ,groundPos1.x, groundPos1.y);
        sb.draw(ground ,groundPos2.x, groundPos2.y);
        sb.draw(ground ,groundPos3.x, groundPos3.y);
        sb.draw(ground ,groundPos4.x, groundPos4.y);

        //for game over
        if (collide){
            sb.draw(gameover, cam.position.x, cam.viewportHeight/2);
        }

//        score screen
        int indexone = score % 10;
        int indexten = (int) Math.floor(score /10);
        String[] number = {"0.png", "1.png" ,"2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png"};


        Texture onth = new Texture(number[indexone]);
        Texture tenth = new Texture(number[indexten]);

        sb.draw(onth ,cam.position.x + 60 , cam.viewportHeight - 50);
        if (indexten != 0)
            sb.draw(tenth ,cam.position.x + 60  - tenth.getWidth() - 5 , cam.viewportHeight - 50);
        sb.end();
    }


    //for delete old one
    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for (Tube tube:tubes){
            tube.dispose();
        }
        for (Coin c : coins){
            c.dispose();
        }
        System.out.println("PlayState Dispose");
    }


    //for Update ground over and over
    public void updateGround(){
        if (cam.position.x - cam.viewportWidth/2 > groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth()*4, 0);
        }
        if (cam.position.x - cam.viewportWidth/2 > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth()*4, 0);
        }
        if (cam.position.x - cam.viewportWidth/2 > groundPos3.x + ground.getWidth()) {
            groundPos3.add(ground.getWidth()*4, 0);
        }
        if (cam.position.x - cam.viewportWidth/2 > groundPos4.x + ground.getWidth()){
            groundPos4.add(ground.getWidth()*4, 0);
        }
    }
}
