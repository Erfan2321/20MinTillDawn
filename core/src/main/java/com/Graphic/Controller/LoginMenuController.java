package com.Graphic.Controller;

import com.Graphic.Controller.MainMenu.MainMenuController;
import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.UserManager;
import com.Graphic.View.*;
import com.Graphic.View.MainMenu.MainMenuView;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static com.Graphic.Model.Enum.Message.*;
import static com.Graphic.View.MainMenu.SettingMenuView.languages;

public class LoginMenuController {

    private ForgetPassView view2;
    private LoginMenuView view;
    private Label errorLabel;
    private Label pass;


    public void setView (LoginMenuView view) {

        this.view = view;
        errorLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
    }
    public void setView (ForgetPassView view) {

        this.view2 = view;
        errorLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
    }

    public void handleForgetMenuButtons() {

        if (view2 != null) {

            if (view2.isSubClicked() && !view2.getAnswerSecQ().getText().isEmpty() && !view2.getName().getText().isEmpty()) {
                Main.getMain().getScreen().dispose();

                if (!UserManager.userExists(view2.getName().getText()))
                    errorLabel.setText(UserNotFound.getMessage(languages));

                else if (checkSecAnswer(view2.getAnswerSecQ().getText(), view2.getName().getText())) {
                    view2.getNewPassword().setVisible(true);
                    view2.getNewPassword().setWidth(500);
                }
                else
                    errorLabel.setText(WrongSecQ.getMessage(languages));

            } else if (view2.isBackClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            } else if (view2.isSubClicked() && view2.getNewPassword().getText().isEmpty()) {

                if (view2.getNewPassword().getText().matches("^(?=.*[@#$%&*)(_])(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$")) {
                    UserManager.loadUser(view2.getName().getText()).setPass(view2.getNewPassword().getText());
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                }
                else
                    errorLabel.setText(weekPassword.getMessage(languages));
            }
        }
    }

    public void handleLoginMenuButtons () {

        if (view != null) {

            if (view.isLoginClicked()) {

                Main.getMain().getScreen().dispose();
                errorLabel.setText(canLogin(view.getName().getText(),
                    view.getPass().getText()));

                if (errorLabel.getText().isEmpty()) {
                    GameAssetManager.getGameAssetManager().currentUser = UserManager.loadUser(view.getName().getText());
                    Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                }

            } else if (view.isBackClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new SignUpMenuView(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin()));

            } else if (view.getForget().isChecked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ForgetPassView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));

            }
        }
    }

    private String canLogin (String name, String pass) {

        if (name.isEmpty() || name.equals("Enter your name"))
            return emptyName.getMessage(languages);

        if (pass.isEmpty() || pass.equals("Enter your password"))
            return emptyPass.getMessage(languages);

        if (!UserManager.userExists(name))
            return UserNotFound.getMessage(languages);

        if (!UserManager.loadUser(name).getPass().equals(pass))
            return IncorrectPass.getMessage(languages);

        return "";
    }
    private boolean checkSecAnswer (String answer, String name) {
        return answer.trim().equals(UserManager.loadUser(name).getSecQuestion());
    }

    public Label getErrorLabel() {

        return errorLabel;
    }
    public Label getPass () {

        return this.pass;
    }
}
