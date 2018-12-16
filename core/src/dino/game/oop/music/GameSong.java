package dino.game.oop.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GameSong implements Song{

    private Music haweii;

    public GameSong() {
        haweii = Gdx.audio.newMusic(Gdx.files.internal("Sound/main.mp3"));
    }

    @Override
    public void play() {
        haweii.play();
    }

    @Override
    public void pause() {
        haweii.pause();
    }

    @Override
    public void SetVolume() {

    }

    @Override
    public void dispose() {
        haweii.dispose();
    }
}
