package com.Graphic.Controller.Game;

import com.Graphic.Model.GameModel.Player;
import com.Graphic.Model.TopBar;
import com.Graphic.View.GameMenuView;
import com.badlogic.gdx.graphics.Camera;


public class GameMenuController {

    private PlayerController pLayerController;
    private WeaponController weaponController;
    private EnemyController enemyController;
    private WorldController worldController;
    private GameMenuView view;
    private Player player;

    public void setView (GameMenuView gameMenuView, Player pLayer) {

        this.player = pLayer;
        this.view = gameMenuView;
        this.enemyController = new EnemyController(player);
        this.pLayerController = new PlayerController(player);
        this.worldController = new WorldController(pLayerController);
        this.weaponController = new WeaponController(pLayer);

    }

    public void update(Camera camera, float time, int MaxTime, TopBar topBar, Boolean damager, boolean speedy) {
        if (view != null) {
            worldController.update();
            pLayerController.update(camera, speedy, time);
            weaponController.update(time, topBar, damager);
            enemyController.update(time, MaxTime);
        }
    }

    public WeaponController getWeaponController() {

        return this.weaponController;
    }
}
