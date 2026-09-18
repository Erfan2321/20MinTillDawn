package com.Graphic.Model.GameModel;

import com.Graphic.View.MainMenu.SettingMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;

/**
 * Shared sound effects. This is a singleton because the sounds are the same everywhere:
 * each instance loads all seven files, and the game used to build six separate instances
 * (one of them per boss) and never free any of them.
 */
public class SFXManager {

    private static SFXManager instance;

    private final HashMap<String, Sound> sounds;

    private SFXManager() {
        sounds = new HashMap<>();
        loadSounds();
    }

    public static SFXManager getInstance() {
        if (instance == null)
            instance = new SFXManager();
        return instance;
    }

    private void loadSounds() {
        load("walk", "sfx/walk.wav");
        load("shoot", "sfx/shoot.wav");
        load("point", "sfx/point.wav");
        load("damage", "sfx/damage.wav");
        load("level_up", "sfx/levelUp.wav");
        load("elderShield", "sfx/elderShield.wav");
        load("enemyDamage", "sfx/enemyDamage.wav");
    }

    private void load(String key, String path) {

        FileHandle file = Gdx.files.internal(path);
        if (!file.exists()) {
            Gdx.app.error("SFXManager", "Missing sound file: " + path);
            return;
        }
        try {
            sounds.put(key, Gdx.audio.newSound(file));
        } catch (RuntimeException e) {
            // Missing or unsupported audio should never stop the game from starting.
            Gdx.app.error("SFXManager", "Could not load " + path, e);
        }
    }

    public void play (String soundKey) {
        Sound sound = sounds.get(soundKey);
        if (sound == null) {
            Gdx.app.error("SFXManager", "Sound not found: " + soundKey);
            return;
        }
        if (SettingMenuView.getInstance().getSfxCheckBox().isChecked())
            sound.play();
    }

    public void dispose() {
        for (Sound sound : sounds.values())
            sound.dispose();

        sounds.clear();
        instance = null;
    }
}
