package com.Graphic.Model.GameModel.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Tree extends Enemy {

    private static Animation<Texture> animation = null;

    public Tree(int x, int y) {
        super(Integer.MAX_VALUE, x, y, 200, 100, false);
    }

    @Override
    public Texture getTexture() {
        return new Texture(Gdx.files.internal("Enemy/Tree/Tree0.png"));
    }

    @Override
    public Animation<Texture> getAnimation() {

        if (animation == null) {
            String path1 = "Enemy/Tree/Tree0.png";
            String path2 = "Enemy/Tree/T_TreeMonster_1.png";
            String path3 = "Enemy/Tree/T_TreeMonster_2.png";
            Texture frame1 = new Texture(path1);
            Texture frame2 = new Texture(path2);
            Texture frame3 = new Texture(path3);

            animation =  new Animation<>(0.1f,
                frame1, frame1, frame1, frame1, frame1, frame1, frame2,
                frame3, frame3, frame3, frame3, frame3, frame3, frame3,
                frame3, frame3, frame3, frame3, frame3, frame3, frame3,
                frame3, frame3, frame3, frame3, frame2);
        }
        return animation;
    }
}
