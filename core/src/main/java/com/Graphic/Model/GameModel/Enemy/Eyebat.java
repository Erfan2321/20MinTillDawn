package com.Graphic.Model.GameModel.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Eyebat extends Enemy {

    private static Animation<Texture> animation = null;
    private static Texture texture = null;

    public Eyebat (int x, int y) {
        super(50, x, y, 60, 60, true);
    }

    @Override
    public Texture getTexture() {

        if (texture == null)
            texture = new Texture(Gdx.files.internal("Enemy/Eyebat/T_EyeBat_0.png"));

        return texture;
    }
    @Override
    public Animation<Texture> getAnimation() {

        if (animation == null) {
            String path1 = "Enemy/Eyebat/T_EyeBat_0.png";
            String path2 = "Enemy/Eyebat/T_EyeBat_1.png";
            String path3 = "Enemy/Eyebat/T_EyeBat_2.png";
            String path4 = "Enemy/Eyebat/T_EyeBat_3.png";
            Texture frame1 = new Texture(path1);
            Texture frame2 = new Texture(path2);
            Texture frame3 = new Texture(path3);
            Texture frame4 = new Texture(path4);

            animation =  new Animation<>(0.1f,
                frame1, frame4, frame2, frame3,
                frame3, frame2, frame4, frame1);
        }
        return animation;
    }
}
