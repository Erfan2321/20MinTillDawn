package com.Graphic.Model.GameModel;

import com.Graphic.Model.Enum.Hero;
import com.Graphic.Model.Enum.WeaponType;
import com.Graphic.Model.Game;
import com.Graphic.Model.GameFiles;
import com.Graphic.Model.GameModel.Enemy.*;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class SaveManager {

    private static final String SAVE_FILE = "savegame.json";

    public void saveGame() {
        SaveData data = new SaveData();
        Player player = Game.getGame().pLayer;
        Weapon weapon = player.getWeapon();

        data.username = player.getUsername();
        data.point = player.getPoint();
        data.kill = player.getKill();
        data.playerHealth = player.getPlayerHealth();
        data.hero = player.getHero().name();
        data.weaponType = weapon.getType().name();
        data.positionX = player.getPosX();
        data.positionY = player.getPosY();
        data.xp = player.getXp();
        data.level = player.getLevel();
        data.MaxTime = Game.getGame().MaxTime;
        data.TimeLeft = Game.getGame().timeLeft;


        data.amogreaseAbility = player.getAmogreaseAbility();
        data.progreaseAbility = player.getProgreaseAbility();
        data.vitalityAbility = player.getVitalityAbility();
        data.damagerAbility = player.getDamagerAbility();
        data.speedyAbility = player.getSpeedyAbility();

        data.ammo = weapon.getAmmo();
        data.maxAmmo = weapon.getMaxAmmo();
        data.projectile = weapon.getProjectile();

        data.enemies = new ArrayList<>();
        for (Enemy enemy : Game.getGame().enemies) {
            EnemyData ed = new EnemyData();
            ed.type = enemy.getClass().getSimpleName(); // مثلاً "Elder" یا "Grunt"
            ed.x = enemy.getX();
            ed.y = enemy.getY();
            data.enemies.add(ed);
        }

        // ذخیره‌سازی با gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(data);

        FileHandle file = GameFiles.data(SAVE_FILE);
        file.writeString(json, false, "UTF-8");
    }

    public void loadGame() {
        FileHandle file = GameFiles.data(SAVE_FILE);
        if (!file.exists()) return;

        String json = file.readString("UTF-8");
        Gson gson = new Gson();
        SaveData data = gson.fromJson(json, SaveData.class);

        Player player = new Player(
            data.username,
            data.point,
            data.kill,
            WeaponType.valueOf(data.weaponType),
            Hero.valueOf(data.hero),
            data.amogreaseAbility,
            data.progreaseAbility,
            data.vitalityAbility,
            data.damagerAbility,
            data.speedyAbility
        );

        player.setPosX(data.positionX);
        player.setPosY(data.positionY);
        player.setPlayerHealth(data.playerHealth);
        player.setXp(data.xp);
        player.setLevel(data.level);
        player.getWeapon().setAmmo(data.ammo);
        player.getWeapon().setMaxAmmo(data.maxAmmo);
        player.getWeapon().increaseProjectile(data.projectile - player.getWeapon().getProjectile());

        Game.getGame().pLayer = player;
        Game.getGame().timeLeft = data.TimeLeft;
        Game.getGame().MaxTime = data.MaxTime;

        // بارگذاری انمی‌ها
        Game.getGame().enemies.clear();
        for (EnemyData ed : data.enemies) {
            Enemy enemy;
            switch (ed.type) {
                case "Elder": enemy = new Elder(ed.x, ed.y); break;
                case "Eyebat": enemy = new Eyebat(ed.x, ed.y); break;
                case "Tree": enemy = new Tree(ed.x, ed.y); break;
                case "Tentacle": enemy = new Tentacle(ed.x, ed.y); break;
                default: continue;
            }
            Game.getGame().enemies.add(enemy);
        }
    }
}
