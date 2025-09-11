package com.Graphic.Model.GameModel;

import com.Graphic.Model.Enum.WeaponType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Weapon {

    private final Texture weaponTexture;
    private final WeaponType type;
    private final Sprite weaponSprite;
    private final int damage;
    private int projectile;
    private int maxAmmo;
    private int ammo;


    public Weapon(WeaponType type) {
        this.type = type;
        weaponTexture = type.getIcon();
        weaponSprite = new Sprite(weaponTexture);
        damage = type.getDamage();
        projectile = type.getProjectile();

        weaponSprite.setX((float) Gdx.graphics.getWidth() / 2 );
        weaponSprite.setY((float) Gdx.graphics.getHeight() / 2);
        weaponSprite.setSize(50,50);

        maxAmmo = type.getMaxAmmo();
    }

    public int getAmmo() {
        return ammo;

    }
    public int getDamage() {
        return damage;

    }
    public int getMaxAmmo() {

        return maxAmmo;
    }
    public int getProjectile() {
        return projectile;

    }
    public WeaponType getType() {

        return type;
    }
    public void setAmmo(int ammo){
        this.ammo = ammo;
    }
    public Sprite getWeaponSprite() {
        return weaponSprite;

    }
    public void increaseMaxAmmo(int maxAmmo) {

        this.maxAmmo += maxAmmo;
    }
    public void setMaxAmmo (int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }
    public void increaseProjectile(int projectile) {
        this.projectile += projectile;

    }
}
