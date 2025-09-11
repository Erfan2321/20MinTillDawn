package com.Graphic.Model.GameModel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Point {
    private float x, y;
    private Sprite sprite;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
        Texture texture = new Texture("Point.png");
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
        this.sprite.setSize(20, 20);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    // گترها برای نیازهای بعدی
    public float getX() { return x; }
    public float getY() { return y; }
    public Sprite getSprite() { return sprite; }
    public CollisionRect getRectangle() {
        return new CollisionRect(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

}
