package com.Graphic.Controller.Game;

import com.Graphic.Main;
import com.Graphic.Model.Game;
import com.Graphic.Model.GameModel.Enemy.*;
import com.Graphic.Model.GameModel.Player;
import com.Graphic.Model.SimpleEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;

import static com.Graphic.Model.GameModel.CollisionRect.checkCollision;
import static com.badlogic.gdx.math.MathUtils.random;

public class EnemyController {

    private int x = -1;
    private Player player;
    private boolean gameStarted = Game.getGame().isNew;
    private boolean bossCreated = false;
    private ArrayList<Enemy> enemies = Game.getGame().enemies;
    private ArrayList<SimpleEffect> deathEffects = new ArrayList<>();


    public EnemyController(Player pLayer) {
        this.player = pLayer;
    }

    public void update (float time, int MaxTime) {

        createEnemy(time, MaxTime);
        enemyStyle();
        animation();
        moveEnemiesToward(player.getPosX(), player.getPosY());
        batShotHandle();
        shotPlayer(time);
        inputHandle();
        hitHeroEffects();

        if (bossCreated)
            UpdateShield(time);

    }


    private void animation() {

        for (Enemy enemy : enemies) {
            Animation<Texture> animation = enemy.getAnimation();

            enemy.getSprite().setRegion(animation.getKeyFrame(enemy.getTime()));

            if (!animation.isAnimationFinished(enemy.getTime()))
                enemy.setTime(enemy.getTime() + Gdx.graphics.getDeltaTime());

            else
                enemy.setTime(0);

            animation.setPlayMode(Animation.PlayMode.LOOP);
            enemy.getSprite().draw(Main.getBatch());
        }
    }
    private void createEnemy (float time1, int MaxTime) {

        int time = (int) time1;

        if (gameStarted)
            createTree();

        if (time % 3 == 2 && x != time)
            createTentacle(time);

        if (time % 3 == 2 && x != time)
            createBatShot();

        if (time > (float) MaxTime /4 && x != time)
            if (time % 10 == 9)
                createEyeBat(time, MaxTime);

        handleElder(time, MaxTime);

        x = time;
    }

    private void createTree() {
        ArrayList<float[]> positions = new ArrayList<>();

        while (positions.size() < 15) {
            float x = random.nextInt(3600);
            float y = random.nextInt(2500);

            boolean tooClose = false;

            for (float[] pos : positions) {
                float dx = pos[0] - x;
                float dy = pos[1] - y;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);
                if (distance < 60) {
                    tooClose = true;
                    break;
                }
            }

            if (!tooClose) {
                positions.add(new float[]{x, y});
                Tree tree = new Tree((int)x, (int)y);
                enemies.add(tree);
            }
        }
        gameStarted = false;
    }
    private void createBoss () {

        if (bossCreated)
            return;

        float x = random.nextInt(3500);
        float y = random.nextInt(2400);

        Elder elder = new Elder((int) x, (int) y);
        enemies.add(elder);
        bossCreated = true;
    }
    private void createBatShot () {

        for (Enemy enemy : enemies)
            if (enemy instanceof Eyebat)
                Game.getGame().batShots.add(new BatShot((int)player.getPlayerSprite().getX(),
                    (int)player.getPlayerSprite().getY(), enemy.getX(), enemy.getY()));

    }
    private void createTentacle (int time) {
        ArrayList<float[]> positions = new ArrayList<>();

        while (positions.size() < (time/30) ) {
            float x = random.nextInt(3600);
            float y = random.nextInt(2500);

            boolean tooClose = false;

            for (float[] pos : positions) {
                float dx = pos[0] - x;
                float dy = pos[1] - y;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);
                if (distance < 60) {
                    tooClose = true;
                    break;
                }
            }

            if (!tooClose) {
                positions.add(new float[]{x, y});
                Tentacle tentacle = new Tentacle((int)x, (int)y);
                enemies.add(tentacle);
            }
        }
    }
    private void createEyeBat (int time, int total) {

        ArrayList<float[]> positions = new ArrayList<>();

        while (positions.size() < (4*time - total + 30)/30 ) {
            float x = random.nextInt(3600);
            float y = random.nextInt(2500);

            boolean tooClose = false;

            for (float[] pos : positions) {
                float dx = pos[0] - x;
                float dy = pos[1] - y;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);
                if (distance < 60) {
                    tooClose = true;
                    break;
                }
            }
            if (!tooClose) {
                positions.add(new float[]{x, y});
                Eyebat eyebat = new Eyebat((int)x, (int)y);
                enemies.add(eyebat);
            }
        }
    }

    private void enemyStyle () {

        for (Enemy enemy : enemies) {
            if (player.getPosX() < enemy.getX() && !enemy.isFlipped()) {
                enemy.getSprite().flip(true, false);
                enemy.setFlipped(true);
            }

            if (player.getPosX() >= enemy.getX() && enemy.isFlipped()) {
                enemy.getSprite().flip(true, false);
                enemy.setFlipped(false);
            }
        }
    }
    private void batShotHandle () {

        Iterator<BatShot> iterator = Game.getGame().batShots.iterator();
        while (iterator.hasNext()) {
            BatShot b = iterator.next();

            if (b.getSprite().getX() < 0 || b.getSprite().getX() > 3740 ||
                b.getSprite().getY() < 0 || b.getSprite().getY() > 2650) {

                iterator.remove();
                continue;
            }

            b.getSprite().draw(Main.getBatch());
            Vector2 direction = new Vector2(
                b.getDestinationX() - b.getStartX(),
                b.getDestinationY() - b.getStartY()
            ).nor();

            float speed = 200f;
            float delta = Gdx.graphics.getDeltaTime();

            b.getSprite().translate(direction.x * speed * delta, direction.y * speed * delta);
        }
    }
    private void moveEnemiesToward(float targetX, float targetY) {

        float speed = 1f;

        for (Enemy enemy : enemies) {

            if (!enemy.isCanWalk())
                continue;

            float enemyX = enemy.getX();
            float enemyY = enemy.getY();

            float dx = targetX - enemyX;
            float dy = targetY - enemyY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            float nx = dx / distance;
            float ny = dy / distance;

            enemy.setX((int) (enemyX + nx * speed));
            enemy.setY((int) (enemyY + ny * speed));
        }
    }

    private void UpdateShield(float time) {
        for (Enemy enemy : enemies)
            if (enemy instanceof Elder)
                ((Elder) enemy).update(time);
    }
    private void handleElder (float time, float MaxTime) {

        if (!bossCreated && time > MaxTime /2 && x != time)
            createBoss();

        if (bossCreated && time % 5 == 3)
            dashEldersTowards(player.getPosX(), player.getPosY());
    }
    private void dashEldersTowards(float targetX, float targetY) {

        float speed = 300f;
        float delta = Gdx.graphics.getDeltaTime();

        for (Enemy e : Game.getGame().enemies) {
            if (e instanceof Elder) {

                Sprite sprite = e.getSprite();
                float currentX = sprite.getX();
                float currentY = sprite.getY();


                Vector2 direction = new Vector2(
                    targetX - currentX,
                    targetY - currentY
                ).nor();


                sprite.translate(
                    direction.x * speed * delta,
                    direction.y * speed * delta
                );
                e.setX((int) (sprite.getX()));
                e.setY((int) (sprite.getY()));
            }
        }
    }

    private void hitHeroEffects() {
        Iterator<SimpleEffect> iterator = deathEffects.iterator();
        while (iterator.hasNext()) {
            SimpleEffect effect = iterator.next();
            effect.draw(Main.getBatch(), Gdx.graphics.getDeltaTime());

            if (effect.isFinished())
                iterator.remove();
        }
    }
    private void shotPlayer(float time) {

        Iterator<BatShot> iterator = Game.getGame().batShots.iterator();

        while (iterator.hasNext()) {
            BatShot batShot = iterator.next();
            if (checkCollision(player.getRectangle(), batShot.getRectangle())) {
                player.decreasePlayerHealth(1);
                deathEffects.add(new SimpleEffect(player.getPosX(), player.getPosY()));
                player.setLastDamageTime(time);
                iterator.remove();
                break;
            }
        }
    }
    private void inputHandle () {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9))
            createBoss();
    }


}
