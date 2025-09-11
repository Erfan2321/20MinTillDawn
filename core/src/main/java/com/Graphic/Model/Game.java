package com.Graphic.Model;

import com.Graphic.Model.Enum.Hero;
import com.Graphic.Model.Enum.WeaponType;
import com.Graphic.Model.GameModel.Enemy.BatShot;
import com.Graphic.Model.GameModel.Enemy.Enemy;
import com.Graphic.Model.GameModel.Player;

import java.util.ArrayList;

public class Game {


    private static Game game = null;
    public Player pLayer;
    public boolean isNew;
    public float timeLeft, MaxTime;
    public ArrayList<BatShot> batShots = new ArrayList<>();
    public ArrayList<Enemy> enemies = new ArrayList<>();

    private Game() {
        this.pLayer = new Player("", 0, 0,
            WeaponType.Revolver, Hero.DASHER, 0,
            0, 0, 0, 0);
    }

    public static Game getGame () {
        if (game == null)
            game = new Game();
        return game;
    }
}
