package dino.game.oop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import dino.game.oop.DinoGame;
import dino.game.oop.sprites.Bird;
import dino.game.oop.sprites.Tube;

public class PlayState extends State{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    private Bird bird;
    private Texture bg;
//    private Tube tube;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    private boolean collide;
    private Texture gameover;
    private Vector3 touchPos;

    private int score = 0;
    private int flag = 0;
    private boolean isjump;

    private Array<Tube> tubes;



    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,200);
        cam.setToOrtho(false, DinoGame.WIDTH / 2, DinoGame.HEIGHT / 2);
        bg = new Texture("day.png");

        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x-cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth/2) + ground.getWidth(), GROUND_Y_OFFSET);

        gameover = new Texture("gameover.png");

        tubes = new Array<Tube>();
        for (int i = 1 ; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i  * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        collide = false;
        score = 0;

    }

    @Override
    protected void handleInput() {
        double campos = cam.position.x + 25 - cam.viewportWidth/2;
//        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//        cam.unproject(touchPos);
        if(Gdx.input.justTouched() && !collide) {
            bird.jump();
            isjump = true;
//            System.out.println(Gdx.input.getX() + "" + Gdx.input.getY());
        }else if(Gdx.input.justTouched()) {
//        }else if(touchPos.x > campos - gameover.getWidth()/2 && touchPos.x < gameover.getWidth()/2 + campos%DinoGame.WIDTH + gameover.getWidth()){
            gsm.set(new PlayState(gsm));
//            bird.jump();
//            collide = false;
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
                    flag++;

                }

                if(tube.collides(bird.getBounds())){
                    collide = true;
                    System.out.println(score);
                }

            }
//score
            int tmp = flag % 4;
            if (tubes.get(tmp).getPostop().x + Tube.TUBE_WIDTH/2 + 0.5 + bird.getBounds().getWidth()/2 <= bird.getPosition().x
                    && isjump &&
                    tubes.get(tmp).getPostop().x + Tube.TUBE_WIDTH/2 + 3 + bird.getBounds().getWidth()/2>= bird.getPosition().x){
                score ++;
                System.out.println(score);
                isjump = false;
            }
//            System.out.println(tubes.get(tmp).getPostop().x + " " + bird.getPosition().x);

            if (bird.getPosition().y < ground.getHeight() + GROUND_Y_OFFSET){
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
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube :  tubes){
            sb.draw(tube.getTopTube(), tube.getPostop().x , tube.getPostop().y);
            sb.draw(tube.getBottomTube(), tube.getPosbottom().x, tube.getPosbottom().y);
        }

        sb.draw(ground ,groundPos1.x, groundPos1.y);
        sb.draw(ground ,groundPos2.x, groundPos2.y);

        if (collide){
            sb.draw(gameover, cam.position.x + 25 - cam.viewportWidth/2, cam.viewportHeight/2);
        }

//        score
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

    public void updateGround(){
        if (cam.position.x - cam.viewportWidth/2 > groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - cam.viewportWidth/2 > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }

//    public boolean collides(){
//        return player.overlaps(boundsTop) || player.overlaps(boundsBottom);
//
//    }
}
