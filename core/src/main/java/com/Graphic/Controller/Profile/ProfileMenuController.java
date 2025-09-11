package com.Graphic.Controller.Profile;

import com.Graphic.Controller.MainMenu.MainMenuController;
import com.Graphic.Controller.SignUpMenuController;
import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.UserManager;
import com.Graphic.View.MainMenu.MainMenuView;
import com.Graphic.View.Profile.ChangeAvatar;
import com.Graphic.View.Profile.ChangePass;
import com.Graphic.View.Profile.ProfileMenuView;
import com.Graphic.View.Profile.ChangeUsername;
import com.Graphic.View.SignUpMenuView;

public class ProfileMenuController {

    private ProfileMenuView view;

    public void setView (ProfileMenuView view) {

        this.view = view;
    }

    public void handleMainMenuButtons () {

        if (view != null) {

            if (view.isBackClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            } else if (view.isChangeAvatarClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ChangeAvatar(new ChangeAvatarController(), GameAssetManager.getGameAssetManager().getSkin()));
            } else if (view.isChangeUsernameClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ChangeUsername(new ChangeUsernameController(), GameAssetManager.getGameAssetManager().getSkin()));
            } else if (view.isChangePasswordClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ChangePass(new ChangePassController(), GameAssetManager.getGameAssetManager().getSkin()));
            } else if (view.isChangeDeleteClicked()) {

                UserManager.deleteUser(GameAssetManager.getGameAssetManager().currentUser.getName());
                GameAssetManager.getGameAssetManager().currentUser = null;
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new SignUpMenuView(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        }
    }
}
