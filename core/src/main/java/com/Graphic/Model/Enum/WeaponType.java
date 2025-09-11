package com.Graphic.Model.Enum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum WeaponType {

    Revolver (20, 1, 1,  6,
        "revolver/Icon.png",
        "revolver/Reload1.png",
        "revolver/Reload2.png",
        "revolver/Reload3.png"),
    Shotgun  (10, 4, 1,  2,
        "shotgun/Icon.png",
        "shotgun/Reload1.png",
        "shotgun/Reload2.png",
        "shotgun/Reload3.png"),
    SMGsDual ( 8, 1, 2, 24,
        "smg/Icon.png",
        "smg/Reload1.png",
        "smg/Reload2.png",
        "smg/Reload3.png");


    private final int damage;
    private final int projectile;
    private final int reloadTime;
    private final int maxAmmo;

    private final String Icon;
    private final String reload1;
    private final String reload2;
    private final String reload3;

    WeaponType(int damage, int projectile, int reloadTime, int maxAmmo,
               String character1Idle0, String character1Idle1,
               String character1Idle2, String character1Idle3) {

        this.damage = damage;
        this.projectile = projectile;
        this.reloadTime = reloadTime;
        this.maxAmmo = maxAmmo;
        Icon = character1Idle0;
        reload1 = character1Idle1;
        reload2 = character1Idle2;
        reload3 = character1Idle3;
    }

    public Texture getIcon () {
        return new Texture(Icon);
    }
    public TextureRegion getReload (int number) {
        if (number == 1)
            return new TextureRegion(new Texture(this.reload1));
        else if (number == 2)
            return new TextureRegion(new Texture(this.reload2));
        else if (number == 3)
            return new TextureRegion(new Texture(this.reload3));
        return null;
    }
    public int getProjectile() {

        return projectile;
    }
    public int getReloadTime() {

        return reloadTime;
    }
    public int getMaxAmmo() {

        return maxAmmo;
    }
    public int getDamage() {

        return damage;
    }
    public static WeaponType fromDisplayName(String displayName) {
        for (WeaponType type : WeaponType.values())
            if (type.toString().equalsIgnoreCase(displayName))
                return type;
        throw new IllegalArgumentException("wrong name!");
    }
}
