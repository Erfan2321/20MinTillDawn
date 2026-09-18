package com.Graphic.Model.GameModel;

import com.Graphic.View.MainMenu.SettingMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;

public class SFXManager {

    private final HashMap<String, Sound> sounds;

    public SFXManager() {
        sounds = new HashMap<>();
        loadSounds();
    }

    private void loadSounds() {
        sounds.put("walk", Gdx.audio.newSound(Gdx.files.internal("sfx/walk.wav"))); //
        sounds.put("shoot", Gdx.audio.newSound(Gdx.files.internal("sfx/shoot.wav"))); //
        sounds.put("point", Gdx.audio.newSound(Gdx.files.internal("sfx/point.wav"))); //
        sounds.put("damage", Gdx.audio.newSound(Gdx.files.internal("sfx/damage.wav"))); //
        sounds.put("level_up", Gdx.audio.newSound(Gdx.files.internal("sfx/levelUp.wav")));
        sounds.put("elderShield", Gdx.audio.newSound(Gdx.files.internal("sfx/elderShield.wav"))); //
        sounds.put("enemyDamage", Gdx.audio.newSound(Gdx.files.internal("sfx/enemyDamage.wav"))); //
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
    }
}
