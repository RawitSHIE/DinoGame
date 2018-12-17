package dino.game.oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dino.game.oop.music.MainSong;
import dino.game.oop.states.GameStateManager;
import dino.game.oop.states.IntroState;
import dino.game.oop.states.MenuState;

public class DinoGame extends ApplicationAdapter {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public static final String Title = "CaveWings";
	private GameStateManager gsm;
	private SpriteBatch batch;
	public MainSong mainSong;

	@Override
	public void create () {
		mainSong = new MainSong();

		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		mainSong.play();
		gsm.push(new IntroState(gsm, mainSong));
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
	}
}
