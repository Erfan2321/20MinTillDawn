package com.Graphic.Controller.MainMenu;

import com.Graphic.Main;
import com.Graphic.Model.Enum.Hero;
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
                       "\n     W  /  A  /  S  /  D   -->  Move       \n\n" +
                        "         Mouse             -->  Aim        \n\n" +
                        "      Left Click           -->  Shoot      \n\n" +
                        "           R               -->  Reload     \n\n" +
                        "          Esc              -->  Pause      \n"
                );
                errorLabel.setSize(440, 400);
            } else if (view.isHeroClicked()) {
                errorLabel.setText(heroTable());
                errorLabel.setSize(460, 560);
            }
            else
                errorLabel.setText("");
        }
    }

    /**
     * Built from the Hero enum so it cannot drift out of step with the real stats. The
     * hardcoded version listed the wrong speeds and left out two of the five heroes.
     */
    private String heroTable() {

        StringBuilder text = new StringBuilder("\n");
        for (Hero hero : Hero.values())
            text.append(String.format("%n   %-9s HP %d   Speed %d%n",
                hero.name().charAt(0) + hero.name().substring(1).toLowerCase(),
                hero.getHP(), hero.getSpeed()));

        return text.toString();
    }

    public TextArea getErrorLabel() {
        errorLabel.setDisabled(true);
        return errorLabel;
    }
}
