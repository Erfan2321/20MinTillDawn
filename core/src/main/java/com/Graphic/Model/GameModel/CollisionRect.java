package com.Graphic.Model.GameModel;

import com.Graphic.Model.GameModel.Enemy.Enemy;

public class CollisionRect {

    float x, y;
    float width, height;
    public CollisionRect(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean collidesWith(CollisionRect rect){
        return x < rect.x + rect.width && y < rect.y + rect.height && x + width > rect.x && y + height > rect.y;
    }

    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
    public void setWidth(float width) {
        this.width = width;
    }
    public void setHeight(float height) {
        this.height = height;
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    public static boolean checkCollision (CollisionRect bullet, CollisionRect enemy) {
        float x1 = bullet.getX();
        float y1 = bullet.getY();
        float w1 = bullet.getWidth();
        float h1 = bullet.getHeight();

        float x2 = enemy.getX();
        float y2 = enemy.getY();
        float w2 = enemy.getWidth();
        float h2 = enemy.getHeight();

        return x1 < x2 + w2 &&
            x1 + w1 > x2 &&
            y1 < y2 + h2 &&
            y1 + h1 > y2;
    }
}
