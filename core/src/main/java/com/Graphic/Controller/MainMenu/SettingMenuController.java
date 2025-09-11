package com.Graphic.Controller.MainMenu;

import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.MusicManager;
import com.Graphic.View.MainMenu.MainMenuView;
import com.Graphic.View.MainMenu.SettingMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SettingMenuController {

    private static SettingMenuController settingMenuController;
    private SettingMenuView view;

    private SettingMenuController() {

    }
    public static SettingMenuController getInstance () {
        if (settingMenuController == null)
            settingMenuController = new SettingMenuController();
        return settingMenuController;
    }
    public void setView (SettingMenuView view) {

        this.view = view;
    }


    public void handleSettingButtons() {

        if (view != null) {

            view.getMusicSelectBox().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    String selected = view.getMusicSelectBox().getSelected();
                    MusicManager.getInstance().changeMusic(selected);
                }
            });

            view.getLang().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    SettingMenuView.languages = view.getLang().getSelected();
                }
            });

            view.getMusicVolumeSlider().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    float volume = view.getMusicVolumeSlider().getValue();
                    MusicManager.getInstance().setVolume(volume);
                }
            });

            // TODO  sfx  auto-reload
            view.getGrayscaleCheckBox().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    ShaderProgram.pedantic = false;
                    GameAssetManager.getGameAssetManager().shader = new ShaderProgram(
                        Gdx.files.internal("shaders/grayscale.vert"),
                        Gdx.files.internal("shaders/grayscale.frag")
                    );

                    if (!GameAssetManager.getGameAssetManager().shader.isCompiled()) {
                        System.err.println("Shader error: " + GameAssetManager.getGameAssetManager().shader.getLog());
                    }
                }
            });

            if (view.isBackButtonPressed()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }



        }
    }
}
