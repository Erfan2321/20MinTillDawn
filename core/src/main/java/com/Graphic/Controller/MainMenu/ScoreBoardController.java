package com.Graphic.Controller.MainMenu;

import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.User;
import com.Graphic.View.MainMenu.ScoreboardView;
import com.Graphic.View.MainMenu.MainMenuView;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoardController {

    private ScoreboardView view;
    private Label errorLabel;

    public void setView (ScoreboardView scoreBoardView) {
        this.view = scoreBoardView;
        this.errorLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
    }

    public void handleHintButtons () {
        if (view != null) {

            if (view.isBackClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }

            if (view.isSortChanged()) {

                ArrayList<User> users = view.getSortedUsers();

                if (view.getSelectedSort().equals("point"))
                    users.sort(Comparator.comparingInt(User::getPoint).reversed().thenComparingInt(User::getMustKill).reversed().thenComparing(User::getName));

                else if (view.getSelectedSort().equals("username"))
                    users.sort(Comparator.comparing(User::getName).thenComparingInt(User::getMustKill).reversed().thenComparing(User::getName));

                else if (view.getSelectedSort().equals("kill"))
                    users.sort(Comparator.comparingInt(User::getMustKill).reversed().thenComparing(User::getName));

                else if (view.getSelectedSort().equals("survival time"))
                    users.sort(Comparator.comparingInt(User::getMustTime).thenComparingInt(User::getMustKill).reversed().thenComparing(User::getName));

                view.setSortedUsers(users);
            }
        }
    }

}
