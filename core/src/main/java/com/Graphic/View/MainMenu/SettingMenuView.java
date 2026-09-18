package com.Graphic.View.MainMenu;

import com.Graphic.Controller.MainMenu.SettingMenuController;
import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.MusicManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SettingMenuView implements Screen {

    private static SettingMenuView settingMenuView = null;
    private final SettingMenuController controller;
    private Stage stage;
    public final Table table;
    public static String languages = "English";


    private Slider musicVolumeSlider;
    private SelectBox<String> musicSelectBox;
    private SelectBox<String> lang;
    private CheckBox sfxCheckBox;
    private CheckBox aimCheckBox;
    private CheckBox autoReloadCheckBox;
    private CheckBox grayscaleCheckBox;
    private TextButton backButton;

    private boolean backButtonPressed;

    Texture backgroundTexture = new Texture(Gdx.files.internal("back11.jpg"));

    private SettingMenuView(SettingMenuController controller, Skin skin) {

        this.controller = controller;
        controller.setView(this);
        table = new Table();

        musicSelectBox = new SelectBox<>(skin);
        musicSelectBox.setItems(MusicManager.getTrackNames());
        musicVolumeSlider = new Slider(0, 1, 0.1f, false, skin);

        lang = new SelectBox<>(skin);
        lang.setItems("English", "French");

        aimCheckBox = new CheckBox("Auto Aim", skin);
        sfxCheckBox = new CheckBox("SFX Enabled", skin);
        autoReloadCheckBox = new CheckBox("Auto Reload", skin);
        grayscaleCheckBox = new CheckBox("Grayscale Mode", skin);

        backButton = new TextButton("Back", skin);
        backButtonPressed = false;

    }

    public static SettingMenuView getInstance() {
        if (settingMenuView == null)
            settingMenuView = new SettingMenuView(SettingMenuController.getInstance(), GameAssetManager.getGameAssetManager().getSkin());

        return settingMenuView;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(delta);
        stage.draw();

        controller.handleSettingButtons();

        backButtonPressed = false;
    }

    @Override public void show() {

        this.stage = new Stage(); // TODO فرقش با وقتی که توش یه اسکرین نیو رپورت بزاری چیه
        Gdx.input.setInputProcessor(stage);
        table.clear();
        table.setFillParent(true);
        table.defaults().pad(10).fillX().uniformX();

        table.add(new Label("Music Volume:", GameAssetManager.getGameAssetManager().getSkin())).left();
        table.add(musicVolumeSlider).row();

        table.add(new Label("Select Music:", GameAssetManager.getGameAssetManager().getSkin())).left();
        table.add(musicSelectBox).row();
        table.add(new Label("Select Language:", GameAssetManager.getGameAssetManager().getSkin())).left();
        table.add(lang).row();

        table.add(sfxCheckBox).colspan(2).row();
        table.add(autoReloadCheckBox).colspan(2).row();
        table.add(aimCheckBox).colspan(2).row();
        table.add(grayscaleCheckBox).colspan(2).row();
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backButtonPressed = true;
            }
        });

        Drawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        table.setBackground(backgroundDrawable);

        table.add(backButton).colspan(2).width(150).height(60).padTop(20);
        stage.addActor(table);
    }
    @Override
    public void resize(int width, int height) {

        if (stage != null)
            stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }

    public Slider getMusicVolumeSlider() {

        return musicVolumeSlider;
    }

    public SelectBox<String> getLang() {

        return lang;
    }
    public SelectBox<String> getMusicSelectBox() {

        return musicSelectBox;
    }
    public CheckBox getAutoReloadCheckBox() {

        return autoReloadCheckBox;
    }
    public CheckBox getAimCheckBox() {

        return aimCheckBox;
    }
    public CheckBox getGrayscaleCheckBox() {

        return grayscaleCheckBox;
    }
    public boolean isBackButtonPressed() {

        return backButtonPressed;
    }
    public CheckBox getSfxCheckBox() {

        return sfxCheckBox;
    }
}

