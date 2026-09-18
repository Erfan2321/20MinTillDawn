package com.Graphic.View.MainMenu;

import com.Graphic.Controller.MainMenu.HintMenuController;
import com.Graphic.Controller.MainMenu.MainMenuController;
import com.Graphic.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.Graphic.Model.Enum.Message.*;
import static com.Graphic.View.MainMenu.SettingMenuView.languages;

public class HintMenuView implements Screen {

    private final HintMenuController controller;
    private final Table table;
    private Stage stage;

    private TextArea heroInfo;
    private TextArea keyInfo;
    private TextArea cheatCode;
    private TextArea showAbility;

    private final TextButton abilityButton;
    private final TextButton cheatButton;
    private final TextButton heroButton;
    private final TextButton keyButton;
    private final TextButton back;

    private boolean keyClicked;
    private boolean backClicked;
    private boolean heroClicked;
    private boolean cheatClicked;
    private boolean abilityClicked;

    Texture backgroundTexture = new Texture(Gdx.files.internal("back11.jpg"));


    public HintMenuView(HintMenuController controller, Skin skin) {

        this.controller = controller;
        controller.setView(this);

        keyInfo = new TextArea("", skin);
        heroInfo = new TextArea("", skin);
        cheatCode = new TextArea("", skin);
        showAbility = new TextArea("", skin);

        keyInfo.setWidth(500);
        heroInfo.setWidth(500);
        cheatCode.setWidth(500);
        showAbility.setWidth(500);
        keyInfo.setDisabled(true);
        heroInfo.setDisabled(true);
        cheatCode.setDisabled(true);
        showAbility.setDisabled(true);

        abilityButton = new TextButton(Abilities.getMessage(languages), skin);
        cheatButton = new TextButton(Cheats.getMessage(languages), skin);
        heroButton = new TextButton(Heroes.getMessage(languages), skin);
        keyButton = new TextButton(Keys.getMessage(languages), skin);
        back = new TextButton(Back.getMessage(languages), skin);

        this.keyClicked = false;
        this.backClicked = false;
        this.heroClicked = false;
        this.cheatClicked = false;
        this.abilityClicked = false;

        table = new Table(skin);
    }

    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.defaults().align(Align.center).pad(10);
        table.left();

        table.add(abilityButton).width(300);
        table.row().pad(20, 0, 10, 0);
        table.add(cheatButton).width(300);
        table.row().pad(20, 0, 10, 0);
        table.add(heroButton).width(300);
        table.row().pad(20, 0, 10, 0);
        table.add(keyButton).width(300);
        table.row().pad(70, 0, 0, 0);
        table.add(back).width(300);


        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backClicked = true;
            }
        });
        keyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keyClicked = !keyClicked;
                cheatClicked = false;
                abilityClicked = false;
                heroClicked = false;
            }
        });
        heroButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                heroClicked = !heroClicked;
                cheatClicked = false;
                abilityClicked = false;
                keyClicked = false;
            }
        });
        cheatButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cheatClicked = !cheatClicked;
                heroClicked = false;
                abilityClicked = false;
                keyClicked = false;
            }
        });
        abilityButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                abilityClicked = !abilityClicked;
                cheatClicked = false;
                heroClicked = false;
                keyClicked = false;
            }
        });

        Drawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        table.setBackground(backgroundDrawable);
        stage.addActor(table);
    }
    public void render(float v) {

        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        controller.handleHintButtons();

        if (!controller.getErrorLabel().getText().isEmpty())
            stage.addActor(controller.getErrorLabel());

        backClicked = false;
    }
    @Override
    public void resize(int width, int height) {

        if (stage != null)
            stage.getViewport().update(width, height, true);
    }
    public void pause() {

    }
    public void resume() {

    }
    public void hide() {

    }
    public void dispose() {

    }

    public boolean isKeyClicked() {
        return keyClicked;
    }

    public boolean isBackClicked() {
        return backClicked;
    }

    public boolean isHeroClicked() {
        return heroClicked;
    }

    public boolean isCheatClicked() {
        return cheatClicked;
    }

    public boolean isAbilityClicked() {
        return abilityClicked;
    }
}
