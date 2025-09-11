package com.Graphic.Controller.MainMenu;

import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.View.MainMenu.MainMenuView;
import com.Graphic.View.MainMenu.PreGameMenuView;

public class PreGameController {

    private PreGameMenuView view;

    public void setView (PreGameMenuView preGameMenuView) {
        this.view = preGameMenuView;
    }

    public void handlePreGAmeButtons () {
        if (view != null)
            if (view.isBackClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
    }
}
