package com.Graphic.Controller;

import com.Graphic.Controller.MainMenu.MainMenuController;
import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.User;
import com.Graphic.Model.UserManager;
import com.Graphic.View.LoginMenuView;
import com.Graphic.View.MainMenu.MainMenuView;
import com.Graphic.View.SignUpMenuView;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static com.Graphic.Model.Enum.Message.*;
import static com.Graphic.View.MainMenu.SettingMenuView.languages;

public class SignUpMenuController {

    private SignUpMenuView view;
    private Label erorrLabel;


    public void setView (SignUpMenuView view) {

        this.view = view;
        erorrLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
    }

    public void handleSignUpMenuButtons() {

        if (view != null) {
            if (view.finishClicked) {

                erorrLabel.setText(canRegister(view.getName().getText(),
                    view.getPass().getText(), view.getSecurityAnswer().getText()));

                if (erorrLabel.getText().isEmpty()) {

                    User user = new User(
                        view.getName().getText(),
                        view.getPass().getText(),
                        view.getSecurityAnswer().getText()
                    );
                    GameAssetManager.getGameAssetManager().currentUser = user;
                    UserManager.saveUser(user);

                    // A successful sign up signs the player in, the same way the guest button does.
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                }
            }
            else if (view.guestClicked) {
                Main.getMain().getScreen().dispose();
                GameAssetManager.getGameAssetManager().currentUser = new User(
                    "Guest" , "1234", "Snoop Dog"
                );
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            } else if (view.loginClicked) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        }
    }

    private String canRegister (String name, String password, String securityAnswer) {

        if (UserManager.userExists(view.getName().getText()))
            return usernameTaken.getMessage(languages);

        if (name.isEmpty())
            return emptyName.getMessage(languages);

        if (password.isEmpty())
            return emptyPass.getMessage(languages);

        if (securityAnswer.isEmpty())
            return emptySecQ.getMessage(languages);

        if (!checkPass(password))
            return weekPassword.getMessage(languages);

        return "";
    }
    private boolean checkPass (String pass) {

        return User.isStrongPassword(pass);
    }
    public Label getErorrLabel() {

        return erorrLabel;
    }
}
