package com.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;

public class MusicManager {
    private static MusicManager instance;
    private Music backgroundMusic;

    private MusicManager() {
        changeMusic("Experience");
    }

    public void changeMusic(String musicPath) {

        String filePath = "music/Experience.mp3";

        switch (musicPath) {

            case "Havana":
                filePath = "music/Havana.mp3";
                break;

            case "Old":
                filePath = "music/Old.mp3";
                break;

            case "Experience":
                filePath = "music/Experience.mp3";
                break;

            case "Rahgozar":
                filePath = "music/Rahgozar.mp3";
                break;

            case "Inja Irane":
                filePath = "music/Inja Irane.mp3";
                break;

                default:
        }
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
    }

    public static MusicManager getInstance() {
        if (instance == null)
            instance = new MusicManager();
        return instance;
    }

    public void play() {
        if (backgroundMusic == null) return;

        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.setLooping(true);
            backgroundMusic.play();
        }
    }
    public void pause() {
        if (backgroundMusic != null) backgroundMusic.pause();
    }
    public void dispose() {
        if (backgroundMusic != null) backgroundMusic.dispose();
    }
    public void setVolume(float volume) {
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(MathUtils.clamp(volume, 0f, 1f));
        }
    }
}
