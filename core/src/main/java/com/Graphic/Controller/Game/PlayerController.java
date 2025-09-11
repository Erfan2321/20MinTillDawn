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
    private SFXManager sfxManager = new SFXManager();
    private float lastWalk = -10000;


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

        int x = player.getPosX();
        int y = player.getPosY();

        if (Gdx.input.isKeyPressed(App.moveUpKey)) {
            x = player.getPosX();
            y = player.getPosY() + speed;
            if (canWalk(x, y))
                player.setPosY(player.getPosY() + speed);
            if (time - lastWalk > 0.3) {
                sfxManager.play("walk");
                lastWalk = time;
            }
        }

        if (Gdx.input.isKeyPressed(App.moveRightKey)) {
            x = player.getPosX() + speed;
            y = player.getPosY();
            if (canWalk(x, y))
                player.setPosX(player.getPosX() + speed);
            if (time - lastWalk > 0.3) {
                sfxManager.play("walk");
                lastWalk = time;
            }
        }

        if (Gdx.input.isKeyPressed(App.moveDownKey)) {
            x = player.getPosX();
            y = player.getPosY() - speed;
            if (canWalk(x, y))
                player.setPosY(player.getPosY() - speed);
            if (time - lastWalk > 0.3) {
                sfxManager.play("walk");
                lastWalk = time;
            }
        }

        if (Gdx.input.isKeyPressed(App.moveLeftKey)) {
            x = player.getPosX() - speed;
            y = player.getPosY();

            if (canWalk(x, y))
                player.setPosX(player.getPosX() - speed);
            player.getPlayerSprite().flip(true, false);
            if (time - lastWalk > 0.3) {
                sfxManager.play("walk");
                lastWalk = time;
            }
        }

        walkToShield(x, y, time);
        smoothFollow(camera, player.getPosX() , player.getPosY());

        Main.getBatch().setProjectionMatrix(camera.combined);
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
        float lerp = 0.08f;

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

        for (Enemy enemy : Game.getGame().enemies)
            if (enemy instanceof Elder)
                if (!((Elder) enemy).isPointInside(x, y))
                    shieldAttack(time);
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
