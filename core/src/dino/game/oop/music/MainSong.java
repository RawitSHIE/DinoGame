package dino.game.oop.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MainSong implements Song{
    public static Music main;

    public MainSong() {
        main = Gdx.audio.newMusic(Gdx.files.internal("Sound/jurass.mp3"));
    }

    @Override
    public void play() {
        main.play();
    }

    @Override
    public void pause() {
        main.pause();
    }

    @Override
    public void SetVolume() {

    }

    @Override
    public void dispose() {
        main.dispose();
    }
}
