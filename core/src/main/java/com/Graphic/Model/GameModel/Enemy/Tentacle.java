package com.Graphic.Model.GameModel.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Tentacle extends Enemy {

    private static Animation<Texture> animation = null;

    public Tentacle (int x, int y) {
        super(25, x, y, 60, 15, true);
    }

    @Override
    public Texture getTexture() {
        return new Texture(Gdx.files.internal("Enemy/Tantacle/TentacleIdle0.png"));
    }

    @Override
    public Animation<Texture> getAnimation() {

        if (animation == null) {
            String path1 = "Enemy/Tantacle/TentacleIdle0.png";
            String path2 = "Enemy/Tantacle/TentacleIdle1.png";
            String path3 = "Enemy/Tantacle/TentacleIdle2.png";
            String path4 = "Enemy/Tantacle/TentacleIdle3.png";
            Texture frame1 = new Texture(path1);
            Texture frame2 = new Texture(path2);
            Texture frame3 = new Texture(path3);
            Texture frame4 = new Texture(path4);

            animation =  new Animation<>(0.1f,
                frame1, frame4, frame3, frame2,
                frame2, frame3, frame4, frame1);
        }
        return animation;
    }

}
