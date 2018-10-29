package dino.game.oop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dino.game.oop.DinoGame;
import dino.game.oop.sprites.Bird;
import dino.game.oop.sprites.Tube;

import java.awt.event.MouseListener;
import java.util.Random;

public class PlayState extends State{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 10;
    private static final int GROUND_Y_OFFSET = -50;

    private Bird bird;
    private Texture bg;
//    private Tube tube;
    private Texture ground;
    private Vector2 groundPos1, groundPos2, groundPos3, groundPos4;
    private boolean collide;
    private Texture gameover;

    private Array<Tube> tubes;

    private Random rand;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, DinoGame.WIDTH / 2, DinoGame.HEIGHT / 2);
        bg = new Texture("day.png");
        rand = new Random();
        ground = new Texture("ground.png");
        bird = new Bird(50,ground.getHeight() + GROUND_Y_OFFSET);
        groundPos1 = new Vector2(cam.position.x/10 - cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x/10 - cam.viewportWidth/2) + ground.getWidth(), GROUND_Y_OFFSET);
        groundPos3 = new Vector2((cam.position.x/10 - cam.viewportWidth/2) + ground.getWidth()*2, GROUND_Y_OFFSET);
        groundPos4 = new Vector2((cam.position.x/10 - cam.viewportWidth/2) + ground.getWidth()*3, GROUND_Y_OFFSET);

        gameover = new Texture("gameover.png");

        tubes = new Array<Tube>();
        for (int i = 1 ; i <= TUBE_COUNT; i++){
            tubes.add(new Tube( i * (rand.nextInt(1000)+ Tube.TUBE_WIDTH)));
        }

        collide = false;

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() && !collide){
            bird.jump();
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

            for (int i = 0 ; i < tubes.size ; i++){
                Tube tube = tubes.get(i);

                if (cam.position.x - (cam.viewportWidth/2) > tube.getPostop().x + tube.getTopTube().getWidth()){
                    tube.reposition(tube.getPostop().x + ((Tube.TUBE_WIDTH + TUBE_SPACING)  * TUBE_COUNT));
                }

                if(tube.collides(bird.getBounds())){
//                    gsm.set(new EndGameState(gsm));
                    collide = true;
                    System.out.println("Gameover");
                }
            }
            if (bird.getPosition().y < ground.getHeight() + GROUND_Y_OFFSET){
//                gsm.set(new EndGameState(gsm));
                collide = true;
                System.out.println("Gameover");
            }
            cam.update();
        }else{
            bird.updateAnimation(dt);
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
        for (Tube tube :  tubes){
            sb.draw(tube.getTopTube(), tube.getPostop().x , tube.getPostop().y);
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
