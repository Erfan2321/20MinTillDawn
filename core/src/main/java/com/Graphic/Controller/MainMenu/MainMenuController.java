package com.Graphic.Controller.MainMenu;

import com.Graphic.Controller.LoginMenuController;
import com.Graphic.Controller.Profile.ProfileMenuController;
import com.Graphic.Main;
import com.Graphic.Model.Enum.Hero;
import com.Graphic.Model.Enum.WeaponType;
import com.Graphic.Model.Game;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.GameModel.SaveManager;
import com.Graphic.Model.GameModel.Weapon;
import com.Graphic.View.*;
import com.Graphic.View.MainMenu.*;
import com.Graphic.View.Profile.ProfileMenuView;

public class MainMenuController {

    private MainMenuView view;

    public void setView (MainMenuView maniMenuView) {
        this.view = maniMenuView;
    }

    public void handleMainMenuButtons () {

        if (view != null) {

            if (view.isNewGameClicked()) {
                Main.getMain().getScreen().dispose();
                Game.getGame();
                Game.getGame().pLayer.setUsername(GameAssetManager.getGameAssetManager().currentUser.getName());
                if (PreGameMenuView.getInstance().getSelectedHero() != null)
                    Game.getGame().pLayer.setHero(Hero.fromDisplayName(PreGameMenuView.getInstance().getSelectedHero()));
                else
                    Game.getGame().pLayer.setHero(Hero.SCARLET);

                if (PreGameMenuView.getInstance().getSelectedWeapon() != null)
                    Game.getGame().pLayer.setWeapon(new Weapon(WeaponType.fromDisplayName(PreGameMenuView.getInstance().getSelectedWeapon())));
                else
                    Game.getGame().pLayer.setWeapon(new Weapon(WeaponType.Revolver));
                Game.getGame().isNew = true;
                Main.getMain().setScreen(new GameMenuView(Game.getGame().pLayer));
            }
            else if (view.isContinueGameClicked()) {
                Main.getMain().getScreen().dispose();
                Game.getGame().isNew = false;
                new SaveManager().loadGame();
                Main.getMain().setScreen(new GameMenuView(Game.getGame().pLayer));
            }
            else if (view.isSettingsClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(SettingMenuView.getInstance());
            }
            else if (view.isLogoutClicked()) {
                Main.getMain().getScreen().dispose();
                GameAssetManager.getGameAssetManager().currentUser = null;
                Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
            else if (view.isPreGameClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(PreGameMenuView.getInstance());
            }
            else if (view.isProfileClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
            else if (view.isScoreboardClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ScoreboardView(new ScoreBoardController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
            else if (view.isTalentClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new HintMenuView(new HintMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        }
    }


}
