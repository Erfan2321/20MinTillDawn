package com.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class GameAssetManager implements Disposable {

    private static GameAssetManager gameAssetManager;
    public ShaderProgram shader;

    private Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    private Texture batShotTexture = null;
    private Texture bulletTexture = null;

    /**
     * The account that is currently signed in. Null until the player signs up, logs in,
     * or continues as a guest.
     */
    public User currentUser;

    private GameAssetManager() {

    }

    public Texture getBatShotTexture () {

        if (batShotTexture == null) {
            String path1 = "Enemy/Eyebat/shot.png";
            batShotTexture = new Texture(path1);
        }
        return batShotTexture;
    }
    public Texture getBulletTexture () {

        if (bulletTexture == null) {
            String path1 = "bullet.png";
            bulletTexture = new Texture(path1);
        }
        return bulletTexture;
    }
    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null)
            gameAssetManager = new GameAssetManager();
        return gameAssetManager;
    }

    public Skin getSkin() {

        return skin;
    }

    public void setSkin(Skin skin) {

        this.skin = skin;
    }

    @Override
    public void dispose() {

        if (skin != null) skin.dispose();
        if (shader != null) shader.dispose();
        if (batShotTexture != null) batShotTexture.dispose();
        if (bulletTexture != null) bulletTexture.dispose();

        skin = null;
        shader = null;
        batShotTexture = null;
        bulletTexture = null;
    }
}
