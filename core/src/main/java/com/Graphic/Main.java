package com.Graphic;

import com.Graphic.Controller.Profile.ChangeAvatarController;
import com.Graphic.Controller.SignUpMenuController;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.User;
import com.Graphic.Model.UserManager;
import com.Graphic.View.SignUpMenuView;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public static ChangeAvatarController unrealController;
    private static Main main;
    private static SpriteBatch batch;

    @Override
    public void create() {

        main = this;
        batch = new SpriteBatch();

        for (int i = 0 ; i < 10 ; i++) {
            User user = new User("Test"+i , "1234", "wer");
            user.setPoint(i*20);
            user.setMustKill(i*10);
            user.setMustTime(i+2);
            UserManager.saveUser(user);
        }
        main.setScreen(new SignUpMenuView(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
    public void setBlackWhiteMode(boolean enabled) {
        batch.setShader(enabled ? GameAssetManager.getGameAssetManager().shader : null);
    }
    public void render() {
       super.render();
    }
    public void dispose() {
        batch.dispose();
    }
    public static SpriteBatch getBatch() {
        return batch;
    }
    public static Main getMain() {
        return main;
    }
    public static void setMain(Main main) {
        Main.main = main;
    }
}
