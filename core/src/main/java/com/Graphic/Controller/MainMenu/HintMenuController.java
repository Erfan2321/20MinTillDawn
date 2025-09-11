package com.Graphic.Controller.MainMenu;

import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.View.MainMenu.HintMenuView;
import com.Graphic.View.MainMenu.MainMenuView;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

public class HintMenuController {

    private HintMenuView view;
    private TextArea errorLabel;

    public void setView (HintMenuView hintMenuView) {
        this.view = hintMenuView;
        this.errorLabel = new TextArea("", GameAssetManager.getGameAssetManager().getSkin());
    }

    public void handleHintButtons () {
        if (view != null) {

            if (view.isBackClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            } else if (view.isAbilityClicked()) {
                errorLabel.setText("");
                errorLabel.appendText("Speedy    : 2 times the player's movement speed for 10 seconds. \n\n");
                errorLabel.appendText("Damager   : Increases weapon damage by 25% for 10 seconds.\n\n");
                errorLabel.appendText("Vitality  : Increases maximum HP by one unit. \n\n");
                errorLabel.appendText("Progrease : One unit increase in Projectile Weapons. \n\n");
                errorLabel.appendText("Amogrease : Increase the maximum number of weapon bullets by 5 units.\n\n");
                errorLabel.setSize(400, 600);
            } else if (view.isCheatClicked()) {
                errorLabel.setText("");
                errorLabel.appendText("       9     -->  go to Boss!    \n\n");
                errorLabel.appendText("       8     -->  infinity ammo   \n\n");
                errorLabel.appendText("       7     -->  increase level   \n\n");
                errorLabel.appendText("       6     -->  increase Health    \n\n");
                errorLabel.appendText("       5     -->  Reducing the playing time to one minute ");
                errorLabel.setSize(500, 600);
            } else if (view.isKeyClicked()) {
                errorLabel.setText(
                       "\n          W           -->  up    \n" +
                        "           D           -->  Right \n" +
                        "           S           -->  Down  \n" +
                        "           A           -->  Left  \n" +
                        "    Mouse Click  -->  Right   \n"
                );
                errorLabel.setSize(400, 400);
            } else if (view.isHeroClicked()) {
                errorLabel.setText(" \n \n");
                errorLabel.appendText("                 Shana                \n\n");
                errorLabel.appendText("        HP : 4          Speed : 4      \n");
                errorLabel.appendText("                Diamond                \n\n");
                errorLabel.appendText("        HP : 7          Speed : 1      \n");
                errorLabel.appendText("                Scarlet                \n\n");
                errorLabel.appendText("        HP : 3          Speed : 5      \n");
                errorLabel.setSize(400, 600);
            }
            else
                errorLabel.setText("");
        }
    }

    public TextArea getErrorLabel() {
        errorLabel.setDisabled(true);
        errorLabel.setPosition(1000, 300);
        return errorLabel;
    }
}
