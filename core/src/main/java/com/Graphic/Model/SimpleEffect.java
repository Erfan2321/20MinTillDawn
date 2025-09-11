package com.Graphic.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class SimpleEffect {

    float x, y;
    float stateTime = 0f;
    static Animation<TextureRegion> animation = null;

    public SimpleEffect (float x, float y) {
        this.x = x;
        this.y = y;

        if (animation == null) {
            Array<TextureRegion> frames = new Array<>();
            for (int i = 0; i < 6; i++) {
                Texture texture = new Texture("death/death" + i + ".png");
                frames.add(new TextureRegion(texture));
            }
            animation = new Animation<>(0.5f/6f, frames, Animation.PlayMode.NORMAL);
        }
    }

    public boolean isFinished() {
        return animation.isAnimationFinished(stateTime);
    }


    public void draw(SpriteBatch batch, float delta) {
        stateTime += delta;
        TextureRegion frame = animation.getKeyFrame(stateTime, false);
        batch.draw(frame, x, y);
    }
}

