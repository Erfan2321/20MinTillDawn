package com.Graphic.Model.GameModel.Enemy;

import com.Graphic.Main;
import com.Graphic.Model.GameModel.CollisionRect;
import com.Graphic.Model.GameModel.SFXManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Elder extends Enemy {

    private static Animation<Texture> animation = null;
    private static Texture shieldTexture = null;
    private static Texture texture = null;
    private final SFXManager shieldSFX = SFXManager.getInstance();

    private boolean shieldActive = false;
    private CollisionRect shieldRect;
    private float shieldDuration = 45f;
    private float shieldTimeElapsed = 0f;
    private float lastSFX = -1000;

    public Elder(int x, int y) {
        super(400, x, y, 100, 75, true);

        if (shieldTexture == null)
            shieldTexture = new Texture("Enemy/Elder/shield.png");

        shieldRect = new CollisionRect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        startShield();
    }

    public void startShield() {
        shieldActive = true;
        shieldTimeElapsed = 0f;
        shieldRect = new CollisionRect(0, 0, 3740, 2650);
    }
    public void update(float time) {

        if (shieldActive) {

            float deltaTime = Gdx.graphics.getDeltaTime();
            shieldTimeElapsed += deltaTime;

            float progress = Math.min(shieldTimeElapsed / shieldDuration, 1f);
            float newWidth = 3740 * (1 - progress);
            float newHeight = 2650 * (1 - progress);

            float newX = (3740 - newWidth) / 2f;
            float newY = (2650 - newHeight) / 2f;

            shieldRect.setX(newX);
            shieldRect.setY(newY);
            shieldRect.setWidth(newWidth);
            shieldRect.setHeight(newHeight);

            render(Main.getBatch(), time);
            if (progress >= 1f)
                destroyShield();

        }
    }
    public void render(SpriteBatch batch, float time) {

        if (shieldActive && shieldTexture != null) {
            batch.draw(shieldTexture, shieldRect.getX(), shieldRect.getY(), shieldRect.getWidth(), shieldRect.getHeight());
            if (time - lastSFX > 0.56) {
                shieldSFX.play("elderShield");
                lastSFX = time;
            }
        }
    }
    public boolean isPointInside(float x, float y) {

        return shieldActive &&
            x >= shieldRect.getX() + 120 &&
            x <= shieldRect.getX() + shieldRect.getWidth() - 135 &&
            y >= shieldRect.getY() + 200 &&
            y <= shieldRect.getY() + shieldRect.getHeight() - 220;
    }
    public void destroyShield() {
        shieldActive = false;
    }

    public boolean isShieldActive() {
        return shieldActive;
    }

    public Animation<Texture> getAnimation() {

        if (animation == null) {
            String path1 = "Enemy/Elder/ElderBrain.png";
            Texture frame1 = new Texture(path1);

            animation = new Animation<>(2f, frame1);
        }
        return animation;
    }
    public Texture getTexture() {

        if (texture == null)
            texture = new Texture(Gdx.files.internal("Enemy/Elder/ElderBrain.png"));

        return texture;
    }
}
