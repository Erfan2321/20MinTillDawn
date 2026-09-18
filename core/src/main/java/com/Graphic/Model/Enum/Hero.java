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

    private Texture[] frames;
    private Animation<Texture> idleAnimation;

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
    /**
     * Idle frames, loaded once per hero and reused. They used to be reloaded on every call,
     * and because idleAnimation() runs once per frame that leaked six textures per frame.
     */
    private Texture[] frames() {

        if (frames == null)
            frames = new Texture[] {
                new Texture(character1_idle0),
                new Texture(character1_idle1),
                new Texture(character1_idle2),
                new Texture(character1_idle3),
                new Texture(character1_idle4),
                new Texture(character1_idle5),
            };

        return frames;
    }
    public Texture getTexture (int number) {

        Texture[] frames = frames();
        if (number < 0 || number >= frames.length)
            number = 0;

        return frames[number];
    }
    public Animation<Texture> idleAnimation() {

        if (idleAnimation == null)
            idleAnimation = new Animation<>(0.1f, frames());

        return idleAnimation;
    }
    public static Hero fromDisplayName(String displayName) {
        for (Hero type : Hero.values())
            if (type.toString().equalsIgnoreCase(displayName))
                return type;
        throw new IllegalArgumentException("wrong name!");
    }
}
