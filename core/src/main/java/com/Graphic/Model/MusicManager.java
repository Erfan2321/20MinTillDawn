package com.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Background music. The playlist is read from {@code assets/music/tracks.txt} so tracks can be
 * added, removed or replaced without touching the code; a track whose file is missing is simply
 * skipped instead of crashing the game.
 */
public class MusicManager {

    private static final String TRACK_LIST = "music/tracks.txt";

    private static MusicManager instance;
    private static LinkedHashMap<String, String> tracks;

    private Music backgroundMusic;
    private float volume = 0.5f;

    private MusicManager() {
        String[] names = getTrackNames();
        if (names.length > 0)
            changeMusic(names[0]);
    }

    /**
     * Playlist entries in {@code Display name|path/to/file.mp3} form, in file order.
     * Lines that are blank, start with {@code #}, or point at a missing file are ignored.
     */
    private static Map<String, String> tracks() {

        if (tracks != null)
            return tracks;

        tracks = new LinkedHashMap<>();
        FileHandle list = Gdx.files.internal(TRACK_LIST);
        if (!list.exists()) {
            Gdx.app.log("MusicManager", "No " + TRACK_LIST + "; running without music");
            return tracks;
        }

        for (String line : list.readString("UTF-8").split("\\r?\\n")) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#"))
                continue;

            int separator = line.indexOf('|');
            if (separator <= 0) {
                Gdx.app.error("MusicManager", "Ignoring malformed track line: " + line);
                continue;
            }

            String name = line.substring(0, separator).trim();
            String path = line.substring(separator + 1).trim();
            if (Gdx.files.internal(path).exists())
                tracks.put(name, path);
            else
                Gdx.app.error("MusicManager", "Track file missing, skipping: " + path);
        }
        return tracks;
    }

    /** Names for the settings menu to offer. Empty when no playable track was found. */
    public static String[] getTrackNames() {

        return tracks().keySet().toArray(new String[0]);
    }

    public void changeMusic(String trackName) {

        String path = tracks().get(trackName);
        if (path == null) {
            Gdx.app.error("MusicManager", "Unknown track: " + trackName);
            return;
        }

        stopCurrent();

        try {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(volume);
            backgroundMusic.play();
        } catch (RuntimeException e) {
            // A broken or unsupported file should not take the whole game down.
            backgroundMusic = null;
            Gdx.app.error("MusicManager", "Could not play " + path, e);
        }
    }

    private void stopCurrent() {

        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
            backgroundMusic = null;
        }
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
        stopCurrent();
    }
    public void setVolume(float volume) {
        this.volume = MathUtils.clamp(volume, 0f, 1f);
        if (backgroundMusic != null)
            backgroundMusic.setVolume(this.volume);
    }
}
