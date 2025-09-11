package com.Graphic.Model.GameModel.Enemy;

import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.GameModel.CollisionRect;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BatShot {

    private Sprite sprite = new Sprite(GameAssetManager.getGameAssetManager().getBatShotTexture());
    private int x;
    private int y;
    private int startX;
    private int startY;

    public BatShot (int x, int y, int x1, int y1) {
        sprite.setSize(20 , 20);
        this.x = x;
        this.y = y;
        sprite.setX(x1);
        sprite.setY(y1);
        startX = x1;
        startY = y1;
    }

    public Sprite getSprite() {
        return sprite;
    }
    public int getDestinationX() {
        return x;
    }
    public CollisionRect getRectangle() {
        return new CollisionRect(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }
    public int getDestinationY() {
        return y;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }
}
