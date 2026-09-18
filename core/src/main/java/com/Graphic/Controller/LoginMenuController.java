package com.Graphic.Controller;

import com.Graphic.Controller.MainMenu.MainMenuController;
import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.User;
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


    public void setView (LoginMenuView view) {

        this.view = view;
        errorLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
    }
    public void setView (ForgetPassView view) {

        this.view2 = view;
        errorLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
    }

    /**
     * Two-step password recovery. The new-password field starts hidden: the first submit
     * checks the security answer and reveals it, the second submit applies the new password.
     */
    public void handleForgetMenuButtons() {

        if (view2 == null)
            return;

        if (view2.isBackClicked()) {
            backToLogin();
            return;
        }
        if (!view2.isSubClicked())
            return;

        String username = view2.getName().getText();
        String answer = view2.getAnswerSecQ().getText();

        if (!view2.getNewPassword().isVisible()) {

            if (username.isEmpty()) {
                errorLabel.setText(emptyName.getMessage(languages));
            } else if (answer.isEmpty()) {
                errorLabel.setText(emptySecQ.getMessage(languages));
            } else if (!UserManager.userExists(username)) {
                errorLabel.setText(UserNotFound.getMessage(languages));
            } else if (!checkSecAnswer(answer, username)) {
                errorLabel.setText(WrongSecQ.getMessage(languages));
            } else {
                errorLabel.setText("");
                view2.getNewPassword().setVisible(true);
            }
            return;
        }

        String newPassword = view2.getNewPassword().getText();
        User user = UserManager.loadUser(username);

        if (user == null) {
            errorLabel.setText(UserNotFound.getMessage(languages));
        } else if (newPassword.isEmpty()) {
            errorLabel.setText(emptyPass.getMessage(languages));
        } else if (!User.isStrongPassword(newPassword)) {
            errorLabel.setText(weekPassword.getMessage(languages));
        } else {
            user.setPassword(newPassword);
            UserManager.saveUser(user);
            backToLogin();
        }
    }

    public void handleLoginMenuButtons () {

        if (view != null) {

            if (view.isLoginClicked()) {

                errorLabel.setText(canLogin(view.getName().getText(),
                    view.getPass().getText()));

                if (errorLabel.getText().isEmpty()) {
                    GameAssetManager.getGameAssetManager().currentUser = UserManager.loadUser(view.getName().getText());
                    Main.getMain().getScreen().dispose();
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

    private void backToLogin () {

        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    private String canLogin (String name, String pass) {

        if (name.isEmpty())
            return emptyName.getMessage(languages);

        if (pass.isEmpty())
            return emptyPass.getMessage(languages);

        if (!UserManager.userExists(name))
            return UserNotFound.getMessage(languages);

        if (!UserManager.loadUser(name).checkPassword(pass))
            return IncorrectPass.getMessage(languages);

        return "";
    }
    private boolean checkSecAnswer (String answer, String name) {
        return answer.trim().equals(UserManager.loadUser(name).getSecQuestion());
    }

    public Label getErrorLabel() {

        return errorLabel;
    }
}
