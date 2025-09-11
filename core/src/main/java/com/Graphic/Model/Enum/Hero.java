package com.Graphic.Model.Enum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public enum Hero {

    SHANA   (4, 2, "Images_grouped_2/Sprite/Idle/hero5/Idle_0.png",
        "Images_grouped_2/Sprite/Idle/hero5/Idle_1.png",
        "Images_grouped_2/Sprite/Idle/hero5/Idle_2.png",
        "Images_grouped_2/Sprite/Idle/hero5/Idle_3.png",
        "Images_grouped_2/Sprite/Idle/hero5/Idle_4.png",
        "Images_grouped_2/Sprite/Idle/hero5/Idle_5.png"),
    DIAMOND (7, 2,"Images_grouped_2/Sprite/Idle/hero4/Idle_0.png",
        "Images_grouped_2/Sprite/Idle/hero4/Idle_1.png",
        "Images_grouped_2/Sprite/Idle/hero4/Idle_2.png",
        "Images_grouped_2/Sprite/Idle/hero4/Idle_3.png",
        "Images_grouped_2/Sprite/Idle/hero4/Idle_4.png",
        "Images_grouped_2/Sprite/Idle/hero4/Idle_5.png"),
    SCARLET (3, 3,"Images_grouped_2/Sprite/Idle/hero2/Idle_0.png",
        "Images_grouped_2/Sprite/Idle/hero2/Idle_1.png",
        "Images_grouped_2/Sprite/Idle/hero2/Idle_2.png",
        "Images_grouped_2/Sprite/Idle/hero2/Idle_3.png",
        "Images_grouped_2/Sprite/Idle/hero2/Idle_4.png",
        "Images_grouped_2/Sprite/Idle/hero2/Idle_5.png"),
    LILITH  (5, 2,"Images_grouped_2/Sprite/Idle/hero8/Idle_0.png",
        "Images_grouped_2/Sprite/Idle/hero8/Idle_1.png",
        "Images_grouped_2/Sprite/Idle/hero8/Idle_2.png",
        "Images_grouped_2/Sprite/Idle/hero8/Idle_3.png",
        "Images_grouped_2/Sprite/Idle/hero8/Idle_4.png",
        "Images_grouped_2/Sprite/Idle/hero8/Idle_5.png"),
    DASHER  (2, 4,"Images_grouped_2/Sprite/Idle/hero3/Idle_0.png",
        "Images_grouped_2/Sprite/Idle/hero3/Idle_1.png",
        "Images_grouped_2/Sprite/Idle/hero3/Idle_2.png",
        "Images_grouped_2/Sprite/Idle/hero3/Idle_3.png",
        "Images_grouped_2/Sprite/Idle/hero3/Idle_4.png",
        "Images_grouped_2/Sprite/Idle/hero3/Idle_5.png");


    private final int HP;
    private final int Speed;
    private final String character1_idle0;
    private final String character1_idle1;
    private final String character1_idle2;
    private final String character1_idle3;
    private final String character1_idle4;
    private final String character1_idle5;

    Hero (int HP, int speed, String character1Idle0, String character1Idle1, String character1Idle2,
          String character1Idle3, String character1Idle4, String character1Idle5) {

        this.HP = HP;
        Speed = speed;
        character1_idle0 = character1Idle0;
        character1_idle1 = character1Idle1;
        character1_idle2 = character1Idle2;
        character1_idle3 = character1Idle3;
        character1_idle4 = character1Idle4;
        character1_idle5 = character1Idle5;
    }

    public int getHP() {

        return HP;
    }
    public int getSpeed() {

        return Speed;
    }
    public Texture getTexture (int number) {

        if (number == 1)
            return new Texture(character1_idle1);
        else if (number == 2)
            return new Texture(character1_idle2);
        else if (number == 3)
            return new Texture(character1_idle3);
        else if (number == 4)
            return new Texture(character1_idle4);
        else if (number == 5)
            return new Texture(character1_idle5);

        return new Texture(character1_idle0);
    }
    public Animation<Texture> idleAnimation() {
        return new Animation<>(0.1f, getTexture(0), getTexture(1), getTexture(2), getTexture(3), getTexture(4), getTexture(5));
    }
    public static Hero fromDisplayName(String displayName) {
        for (Hero type : Hero.values())
            if (type.toString().equalsIgnoreCase(displayName))
                return type;
        throw new IllegalArgumentException("wrong name!");
    }
}
