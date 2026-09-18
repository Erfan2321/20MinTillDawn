package com.Graphic.Model.GameModel;

import com.Graphic.Model.Enum.Hero;
import com.Graphic.Model.Enum.WeaponType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {

//    private boolean isPlayerIdle = true;
//    private boolean isPlayerRunning = false;

    private final Sprite playerSprite;
    private final Texture texture;
    private String username;

    private int point;
    private int kill;
    private int playerHealth;

    private Hero hero;
    private Weapon weapon;
    private CollisionRect rectangle;

    private float lastDamageTime = 0;
    private float time = 0;
    private int PositionX = Gdx.graphics.getWidth() / 2;
    private int PositionY = Gdx.graphics.getHeight() / 2;

    private int xp;
    private int level;

    private int amogreaseAbility;
    private int progreaseAbility;
    private int vitalityAbility;
    private int damagerAbility;
    private int speedyAbility;

    private SFXManager sfxManager = SFXManager.getInstance();

    public Player(String username, int point, int kill, WeaponType weapon, Hero hero,
                  int amogreaseAbility, int progreaseAbility, int speedyAbility, int damagerAbility,
                  int vitalityAbility) {

        this.amogreaseAbility = amogreaseAbility;
        this.progreaseAbility = progreaseAbility;
        this.vitalityAbility = vitalityAbility;
        this.damagerAbility = damagerAbility;
        this.speedyAbility = speedyAbility;


        this.xp = 0;
        this.level = 1;
        this.point = point;
        this.kill = kill;
        this.hero = hero;
        this.playerHealth = hero.getHP();
        texture = (hero.getTexture(0));
        this.weapon = new Weapon(weapon);

        this.username = username;
        playerSprite = new Sprite(texture);
        playerSprite.setSize(50, 50);
        playerSprite.setPosition(PositionX, PositionY);
        rectangle = new CollisionRect(PositionX, PositionY, playerSprite.getWidth(), playerSprite.getHeight());
    }

    public Hero getHero() {
        return hero;
    }
    public Texture getTexture() {
        return texture;
    }
    public Sprite getPlayerSprite() {
        return playerSprite;
    }
    public CollisionRect getRectangle() {
        return rectangle;
    }

    public int getPosY() {
        return PositionY;
    }
    public int getPosX() {
        return PositionX;
    }
    public void setPosX(int positionX) {
        PositionX = positionX;
        playerSprite.setX(positionX);
        rectangle.setX(positionX);
    }
    public void setPosY(int positionY) {
        PositionY = positionY;
        playerSprite.setY(positionY);
        rectangle.setY(positionY);
    }

    public void draw(Batch batch) {
        playerSprite.draw(batch);
    }

    public float getTime() {
        return time;
    }
    public void setTime(float time) {
        this.time = time;
    }

    public String getUsername() {

        return username;
    }
    public int getKill() {
        return kill;
    }
    public int getPoint() {
        return point;
    }
    public void increasePoint(int point) {
        this.xp += point;
    }
    public int getPlayerHealth() {
        return playerHealth;
    }
    public void increaseHealth (int i) {
        this.playerHealth += i;
    }
    public void cheatIncreaseHealth () {
        if (this.playerHealth < hero.getHP())
            this.playerHealth += 1;
    }
    public void decreasePlayerHealth(int playerHealth) {
        this.playerHealth -= playerHealth;
        sfxManager.play("damage");
    }

    public Weapon getWeapon() {
        return weapon;
    }
    public void checkLevel () {
        if (xp > (level+1)*20) {
            xp = 0;
            level++;
        }
    }
    public int getXp() {
        return xp;
    }
    public int getLevel() {
        return level;
    }
    public int exNeeded () {
        return (level+1)*20;
    }

    public int getAmogreaseAbility() {
        return amogreaseAbility;
    }
    public int getProgreaseAbility() {
        return progreaseAbility;
    }
    public int getVitalityAbility() {
        return vitalityAbility;
    }
    public int getDamagerAbility() {
        return damagerAbility;
    }
    public int getSpeedyAbility() {
        return speedyAbility;
    }

    public void increaseAmogreaseAbility(int amogreaseAbility) {
        this.amogreaseAbility += amogreaseAbility;
    }
    public void increaseProgreaseAbility(int progreaseAbility) {
        this.progreaseAbility += progreaseAbility;
    }
    public void increaseVitalityAbility(int vitalityAbility) {
        this.vitalityAbility += vitalityAbility;
    }
    public void increaseDamagerAbility(int damagerAbility) {
        this.damagerAbility += damagerAbility;
    }
    public void increaseSpeedyAbility(int speedyAbility) {
        this.speedyAbility += speedyAbility;
    }

    public boolean canDamage (float time) {
        return time > this.lastDamageTime + 3;
    }
    public void setLastDamageTime(float lastDamageTime) {
        this.lastDamageTime = lastDamageTime;
    }

    public void cheatLevel() {
        this.level += 1;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }
}
