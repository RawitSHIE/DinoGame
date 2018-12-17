package dino.game.oop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dino.game.oop.DinoGame;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = DinoGame.WIDTH;
        config.height = DinoGame.HEIGHT;
        config.title = DinoGame.Title;
        config.resizable = false;

		new LwjglApplication(new DinoGame(), config);
	}
}
