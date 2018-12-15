package dino.game.oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dino.game.oop.states.GameStateManager;
import dino.game.oop.states.MenuState;

public class DinoGame extends ApplicationAdapter {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public static final String Title = "DinoRun";
	private GameStateManager gsm;
	private SpriteBatch batch;
	private Music  music;

//	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		gsm.push(new MenuState(gsm));
		music = Gdx.audio.newMusic(Gdx.files.internal("Sound/Main.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		music.dispose();
	}
}
