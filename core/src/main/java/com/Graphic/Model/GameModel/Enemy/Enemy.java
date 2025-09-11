package com.Graphic.Model.GameModel.Enemy;

import com.Graphic.Model.GameModel.CollisionRect;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy {

    private boolean flipped = false;
    private boolean canWalk;
    private int health;
    private int x, y;
    private final int height, width;
    private float time = 0;

    private Sprite enemySprite;

    public Enemy(int health, int x, int y, int height, int width, boolean canWalk) {

        this.health = health;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.canWalk = canWalk;

        enemySprite = new Sprite(getTexture());
        enemySprite.setX(this.x);
        enemySprite.setY(this.y);
        enemySprite.setSize(this.width, this.height);
    }
    public float getTime() {
        return time;
    }
    public void setTime(float time) {
        this.time = time;
    }
    public Sprite getSprite () {
        return this.enemySprite;
    }
    public int getHealth() {
        return health;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isFlipped() {
        return flipped;
    }
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public void setX(int x) {
        this.x = x;
        this.enemySprite.setX(x);
    }

    public void setY(int y) {
        this.y = y;
        this.enemySprite.setY(y);
    }
    public boolean isCanWalk() {
        return canWalk;
    }


    public CollisionRect getRectangle() {
        if (this instanceof Tree)
            return new CollisionRect(enemySprite.getX()-5, enemySprite.getY()-5, enemySprite.getWidth()-5, enemySprite.getHeight()-5);
        return new CollisionRect(enemySprite.getX(), enemySprite.getY(), enemySprite.getWidth(), enemySprite.getHeight());
    }
    public void decreaseHealth (int amount) {
        this.health -= amount;
    }
    public abstract Texture getTexture ();
    public abstract Animation<Texture> getAnimation ();
}
