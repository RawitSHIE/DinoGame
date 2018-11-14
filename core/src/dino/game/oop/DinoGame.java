package dino.game.oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dino.game.oop.states.GameStateManager;
import dino.game.oop.states.MenuState;

public class DinoGame extends ApplicationAdapter {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String Title = "DinoRun";
	private GameStateManager gsm;
	private SpriteBatch batch;

//	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
//		img = new Texture("badlogic.jpg");
		Gdx.gl.glClearColor(0, 1, 0, 1);
		gsm.push(new MenuState(gsm));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
//		batch.draw(img, 0, 0);
		batch.end();
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);


	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}
}
