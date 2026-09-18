package com.Graphic.Controller.Profile;

import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.User;
import com.Graphic.Model.UserManager;
import com.Graphic.View.Profile.ChangePass;
import com.Graphic.View.Profile.ProfileMenuView;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static com.Graphic.Model.Enum.Message.*;
import static com.Graphic.View.MainMenu.SettingMenuView.languages;

public class ChangePassController {

    private ChangePass view;
    private Label errorLabel;

    public void setView (ChangePass view) {
        this.view = view;
        this.errorLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
    }

    public void handleChangePass () {

        if (view != null) {

            if (view.isBackClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            } else if (view.isSubClicked()) {
                errorLabel.setText(checkPass(view.getName().getText()));

                if (errorLabel.getText().isEmpty()) {
                    errorLabel.setText(ChangeName.getMessage(languages));
                    GameAssetManager.getGameAssetManager().currentUser.setPassword(view.getName().getText());
                    UserManager.saveUser(GameAssetManager.getGameAssetManager().currentUser);
                }
            }
        }
    }

    public Label getErrorLabel() {

        return errorLabel;
    }
    private String checkPass (String pass) {

        if (pass.isEmpty())
            return emptyPass.getMessage(languages);

        if (GameAssetManager.getGameAssetManager().currentUser.checkPassword(pass))
            return "Please enter new Password";

        if (!User.isStrongPassword(pass))
            return weekPassword.getMessage(languages);

        return "";
    }
}
