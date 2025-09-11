package com.Graphic.Model.GameModel;

import com.Graphic.Model.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet {

    private Sprite sprite = new Sprite(GameAssetManager.getGameAssetManager().getBulletTexture());
    private int x;
    private int y;

    public Bullet(int x, int y, int x1, int y1) {
        sprite.setSize(20 , 20);
        this.x = x;
        this.y = y;
        sprite.setX(x1);
        sprite.setY(y1);
    }

    public Sprite getSprite() {
        return sprite;
    }
    public int getX() {
        return x;
    }

    public CollisionRect getRectangle() {
        return new CollisionRect(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public int getY() {
        return y;
    }
}
