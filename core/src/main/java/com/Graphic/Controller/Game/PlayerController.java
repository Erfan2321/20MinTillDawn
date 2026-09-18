package com.Graphic.Controller.Game;

import com.Graphic.Main;
import com.Graphic.Model.App;
import com.Graphic.Model.Game;
import com.Graphic.Model.GameModel.Enemy.Elder;
import com.Graphic.Model.GameModel.Enemy.Enemy;
import com.Graphic.Model.GameModel.Player;
import com.Graphic.Model.GameModel.SFXManager;
import com.Graphic.Model.SimpleEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayerController {

    private Player player;
    private ArrayList<SimpleEffect> deathEffects = new ArrayList<>();
    private static final float REFERENCE_FPS = 60f;

    private SFXManager sfxManager = SFXManager.getInstance();
    private float lastWalk = -10000;
    private float pendingX;
    private float pendingY;
    private boolean facingLeft;


    public PlayerController(Player pLayer) {
        this.player = pLayer;
    }
    public void update (Camera camera, boolean speedy, float time) {

        idleAnimation();
        move(camera, speedy, time);
        player.draw(Main.getBatch());
        player.checkLevel();
    }


    public void move(Camera camera, boolean speedy, float time) {

        int speed = player.getHero().getSpeed();
        if (speedy)
            speed *= 2;

        // Hero speeds are tuned as pixels per frame at 60 FPS. Scaling by real elapsed time
        // keeps the game playing the same on a 144 Hz monitor as on a 60 Hz one.
        float step = speed * REFERENCE_FPS * Gdx.graphics.getDeltaTime();

        float dx = 0f;
        float dy = 0f;

        if (Gdx.input.isKeyPressed(App.moveUpKey))    dy += step;
        if (Gdx.input.isKeyPressed(App.moveDownKey))  dy -= step;
        if (Gdx.input.isKeyPressed(App.moveRightKey)) dx += step;
        if (Gdx.input.isKeyPressed(App.moveLeftKey))  dx -= step;

        boolean walking = dx != 0f || dy != 0f;

        if (dx != 0f)
            setFacingLeft(dx < 0f);

        if (walking && time - lastWalk > 0.3) {
            sfxManager.play("walk");
            lastWalk = time;
        }

        // Positions are whole pixels, so carry the sub-pixel remainder over to the next frame
        // instead of truncating it away, which would stall movement at high frame rates.
        pendingX += dx;
        pendingY += dy;
        int moveX = (int) pendingX;
        int moveY = (int) pendingY;
        pendingX -= moveX;
        pendingY -= moveY;

        if (moveX != 0 && canWalk(player.getPosX() + moveX, player.getPosY()))
            player.setPosX(player.getPosX() + moveX);

        if (moveY != 0 && canWalk(player.getPosX(), player.getPosY() + moveY))
            player.setPosY(player.getPosY() + moveY);

        walkToShield(player.getPosX(), player.getPosY(), time);
        smoothFollow(camera, player.getPosX() , player.getPosY());

        Main.getBatch().setProjectionMatrix(camera.combined);
    }

    /** The sprite used to be flipped every frame while walking left, so it strobed. */
    private void setFacingLeft (boolean left) {

        if (left == facingLeft)
            return;

        facingLeft = left;
        player.getPlayerSprite().flip(true, false);
    }
    public void idleAnimation() {

        Animation<Texture> animation = player.getHero().idleAnimation();

        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime()))
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());

        else
            player.setTime(0);


        animation.setPlayMode(Animation.PlayMode.LOOP);
    }
    public static void smoothFollow(Camera camera, float targetX, float targetY) {
        // 0.08 per frame at 60 FPS, expressed so the camera eases at the same rate whatever
        // the frame rate is.
        float lerp = 1f - (float) Math.pow(1f - 0.08f, Gdx.graphics.getDeltaTime() * REFERENCE_FPS);

        Vector3 position = camera.position;

        position.x += (targetX - position.x) * lerp;
        position.y += (targetY - position.y) * lerp;

        camera.position.set(position);
        camera.update();
    }
    private boolean canWalk (int x, int y) {

        return x < 3740 && x > 0 && y < 2650 && y > 0;
    }
    private void walkToShield (int x, int y, float time) {

        for (Enemy enemy : Game.getGame().enemies) {
            if (!(enemy instanceof Elder))
                continue;

            Elder elder = (Elder) enemy;
            if (elder.isShieldActive() && !elder.isPointInside(x, y))
                shieldAttack(time);
        }
    }
    private void shieldAttack (float time) {

        if (player.canDamage(time)) {
            player.decreasePlayerHealth(1);
            deathEffects.add(new SimpleEffect(player.getPosX(), player.getPosY()));
            player.setLastDamageTime(time);
        }

        Iterator<SimpleEffect> iterator = deathEffects.iterator();
        while (iterator.hasNext()) {
            SimpleEffect effect = iterator.next();
            effect.draw(Main.getBatch(), Gdx.graphics.getDeltaTime());

            if (effect.isFinished())
                iterator.remove();
        }
    }
}
