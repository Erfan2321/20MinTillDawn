package com.Graphic.Controller.Game;

import com.Graphic.Main;
import com.Graphic.Model.*;
import com.Graphic.Model.GameModel.*;
import com.Graphic.Model.GameModel.Enemy.Enemy;
import com.Graphic.Model.GameModel.Enemy.Tree;
import com.Graphic.Model.GameModel.Point;
import com.Graphic.View.MainMenu.SettingMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Iterator;

import static com.Graphic.Model.GameModel.CollisionRect.checkCollision;

public class WeaponController {

    private Weapon weapon;
    private Player player;
    private boolean showTextures = false;
    private float showDuration = 1f;
    private float showTimer = 0f;
    private int damage;
    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<SimpleEffect> deathEffects = new ArrayList<>();

    private static final float REFERENCE_FPS = 60f;

    private final SFXManager sfx = SFXManager.getInstance();

    public WeaponController(Player player){
        this.player = player;
        this.weapon = player.getWeapon();
        this.damage = weapon.getDamage();
    }

    public void update(float time, TopBar topBar, boolean damager) {
        setWeaponPos();
        weapon.getWeaponSprite().draw(Main.getBatch());
        updateBullets();

        if (weapon.getAmmo() <= 0)
            if (SettingMenuView.getInstance().getAutoReloadCheckBox().isChecked())
                reload();

        if (Gdx.input.isKeyPressed(App.ReloadKey))
            reload();

        if (showTextures)
            drawReload();

        shotEnemy(damager);
        for (Point point : points)
            point.draw(Main.getBatch());
        checkPointOrEnemy(time, topBar);
        hitEffects();
    }
    private void drawReload () {
        showTimer += Gdx.graphics.getDeltaTime();

        int x = player.getPosX() - 15;
        int y = player.getPosY() - 5;

        Main.getBatch().draw(weapon.getType().getReload(1), x, y, 30, 30);
        Main.getBatch().draw(weapon.getType().getReload(2), x, y, 30, 30);
        Main.getBatch().draw(weapon.getType().getReload(3), x, y, 30, 30);

        if (showTimer > showDuration) {
            showTimer = 0f;
            showTextures = false;
        }
    }
    public void setWeaponPos () {
        weapon.getWeaponSprite().setX(player.getPosX() + 15);
        weapon.getWeaponSprite().setY(player.getPosY());
        weapon.getWeaponSprite().setSize(35, 40);
    }
    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getWeaponSprite();

        float weaponCenterX = player.getPosX();
        float weaponCenterY = player.getPosY();

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation( -angle * MathUtils.radiansToDegrees);
    }
    private void hitEffects() {

        Iterator<SimpleEffect> iterator = deathEffects.iterator();
        while (iterator.hasNext()) {
            SimpleEffect effect = iterator.next();
            effect.draw(Main.getBatch(), Gdx.graphics.getDeltaTime());

            if (effect.isFinished())
                iterator.remove();
        }
    }

    public void setCurser (Camera camera) {
        Enemy closeEnemy = getClosestEnemyInRadius(player.getPosX(), player.getPosY(), 500);

        if (closeEnemy != null) {

            Vector3 worldPos = new Vector3(closeEnemy.getX(), closeEnemy.getY(), 0);
            camera.project(worldPos);

            int x = (int) worldPos.x;
            int y = (int) worldPos.y;

            int flippedY = Gdx.graphics.getHeight() - y;

            Gdx.input.setCursorPosition(x, flippedY);
        }
    }

    public void handleWeaponShoot (int x, int y) {

        if (!showTextures && weapon.getAmmo() > 0) {

            int x2 = Game.getGame().pLayer.getPosX(), y2 = Game.getGame().pLayer.getPosY();

            int dx = x - x2;
            int dy = y - y2;
            int check = weapon.getProjectile() / 2;

            for (int i = 0; i < weapon.getProjectile(); i++) {
                if (dy >= dx && dy >= -dx)
                    bullets.add(new Bullet(x + check * 40, y, x2, y2));
                else if (dy <= dx && dy >= -dx)
                    bullets.add(new Bullet(x, y + check * 20, x2, y2));
                else if (dy <= dx && dy <= -dx)
                    bullets.add(new Bullet(x, y + check * 20, x2, y2));
                else if (dy >= dx && dy <= -dx)
                    bullets.add(new Bullet(x + check * 40, y, x2, y2));
                check--;
            }
            sfx.play("shoot");
            weapon.setAmmo(weapon.getAmmo() - 1);
        }
    }
    public void updateBullets() {

        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet b = iterator.next();

            if (b.getSprite().getX() < 0 || b.getSprite().getX() > 3740 ||
                b.getSprite().getY() < 0 || b.getSprite().getY() > 2650) {

                iterator.remove();
                continue;
            }

            b.getSprite().draw(Main.getBatch());
            Vector2 direction = new Vector2(
                Gdx.graphics.getWidth()/2f - b.getX(),
                Gdx.graphics.getHeight()/2f - b.getY()
            ).nor();

            // 20 pixels per frame at 60 FPS, scaled by real time so bullet speed does not
            // depend on the monitor's refresh rate.
            float step = 20f * REFERENCE_FPS * Gdx.graphics.getDeltaTime();
            b.getSprite().setX(b.getSprite().getX() - direction.x * step);
            b.getSprite().setY(b.getSprite().getY() + direction.y * step);
        }
    }
    private int getDamage (boolean damager) {
        if (damager)
            return (damage*5)/4;
        else
            return damage;
    }
    private void reload () {
        showTextures = true;
        showTimer = 0f;
        weapon.setAmmo(weapon.getMaxAmmo());
    }
    private void shotEnemy(boolean damager) {
        Iterator<Bullet> iterator = bullets.iterator();

        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();

            Iterator<Enemy> enemyIterator = Game.getGame().enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();

                if (checkCollision(enemy.getRectangle(), bullet.getRectangle())) {

                    enemy.decreaseHealth(getDamage(damager));
                    sfx.play("enemyDamage");
                    iterator.remove();

                    if (enemy.getHealth() <= 0) {
                        points.add(new Point(enemy.getX(), enemy.getY()));
                        deathEffects.add(new SimpleEffect(enemy.getX(), enemy.getY()));
                        enemyIterator.remove();

                    } else
                       moveEnemyAfterShot(enemy);
                    break;
                }
            }
        }
    }
    public Enemy getClosestEnemyInRadius(int playerX, int playerY, int radius) {

        Enemy closestEnemy = null;
        int closestDistSq = radius * radius;

        for (Enemy enemy : Game.getGame().enemies) {
            if (enemy instanceof Tree)
                continue;

            int dx = ((int) enemy.getSprite().getX() - playerX);
            int dy = ((int) enemy.getSprite().getY() - playerY);
            int distSq = (dx * dx) + (dy * dy);

            if (distSq <= closestDistSq) {
                closestDistSq = distSq;
                closestEnemy = enemy;
            }
        }
        if (closestEnemy != null) {
            System.out.println("Enemy : " + closestEnemy.getX() + "," + closestEnemy.getY());
            System.out.println("Player : " + player.getPosX() + ", " + player.getPosY());
        }
        return closestEnemy;
    }

    private void moveEnemyAfterShot (Enemy enemy) {

        if (enemy instanceof Tree)
            return;

        Vector2 knockbackDir = new Vector2(
            enemy.getX() + enemy.getSprite().getWidth() / 2f - player.getPlayerSprite().getX(),
            enemy.getY() + enemy.getSprite().getHeight() / 2f - player.getPlayerSprite().getY()
        ).nor();

        float knockbackDistance = 10f;
        enemy.setX((int) (enemy.getX() + knockbackDir.x * knockbackDistance));
        enemy.setY((int) (enemy.getY() + knockbackDir.y * knockbackDistance));
    }
    private void checkPointOrEnemy (float time, TopBar topBar) {

        Iterator<Point> pointIterator = points.iterator();
        while (pointIterator.hasNext()) {
            Point p = pointIterator.next();
            if (checkCollision(p.getRectangle(), player.getRectangle())) {
                pointIterator.remove();
                player.increasePoint(3);
                topBar.increase(3);
                sfx.play("point");
            }
        }


        for (Enemy e : Game.getGame().enemies)
            if (checkCollision(e.getRectangle(), player.getRectangle()))
                if (player.canDamage(time)) {
                    player.decreasePlayerHealth(1);
                    deathEffects.add(new SimpleEffect(player.getPosX(), player.getPosY()));
                    player.setLastDamageTime(time);
                }
    }

}
