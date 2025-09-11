package com.Graphic.Controller.Profile;

import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.UserManager;
import com.Graphic.View.Profile.ChangeUsername;
import com.Graphic.View.Profile.ProfileMenuView;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static com.Graphic.Model.Enum.Message.*;
import static com.Graphic.View.MainMenu.SettingMenuView.languages;

public class ChangeUsernameController {

    private ChangeUsername view;
    private Label errorLabel;

    public void setView (ChangeUsername view) {
        this.errorLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        this.view = view;
    }

    public void handleChangeUsernameButtons () {

        if (view != null) {

            if (view.isBackClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            } else if (view.isSubClicked()) {
                errorLabel.setText(checkName(view.getName().getText()));

                if (errorLabel.getText().isEmpty()) {
                    errorLabel.setText(ChangeName.getMessage(languages));
                    UserManager.deleteUser(GameAssetManager.getGameAssetManager().currentUser.getName());
                    GameAssetManager.getGameAssetManager().currentUser.setName(view.getName().getText());
                    UserManager.saveUser(GameAssetManager.getGameAssetManager().currentUser);
                }
            }
        }
    }

    private String checkName (String name) {

        if (name.isEmpty() || name.equals("Enter your name"))
            return emptyName.getMessage(languages);
        if (name.equals(GameAssetManager.getGameAssetManager().currentUser.getName()))
            return "pls enter new Username";
        if (UserManager.userExists(view.getName().getText()))
            return usernameTaken.getMessage(languages);

        return "";
    }
    public Label getErrorLabel() {

        return errorLabel;
    }
}
