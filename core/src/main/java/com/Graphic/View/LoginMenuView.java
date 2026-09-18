package com.Graphic.View;

import com.Graphic.Controller.LoginMenuController;
import com.Graphic.Main;
import com.Graphic.Model.MusicManager;
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

public class LoginMenuView implements Screen {

    private final LoginMenuController controller;
    private Stage stage;
    public Table table;

    private final Label title;

    private final TextField name;
    private final TextField pass;

    private final TextButton login;
    private final TextButton back;
    private final TextButton forget;

    private boolean backClicked;
    private boolean loginClicked;

    Texture backgroundTexture = new Texture(Gdx.files.internal("back14.png"));

    public LoginMenuView (LoginMenuController loginMenuController, Skin skin) {

        this.controller = loginMenuController;
        controller.setView(this);

        this.title = new Label(LoginMenu.getMessage(languages), skin);

        this.name = new TextField("", skin);
        this.name.setMessageText(enterName.getMessage(languages));
        this.pass = new TextField("", skin);
        this.pass.setMessageText(enterPass.getMessage(languages));
        this.pass.setPasswordCharacter('*');
        this.pass.setPasswordMode(true);

        this.back = new TextButton(Back.getMessage(languages), skin);
        this.login = new TextButton(Login.getMessage(languages), skin);
        this.forget = new TextButton(ForgetPass.getMessage(languages), skin);

        this.loginClicked = false;
        this.backClicked = false;

        this.table = new Table(skin);
    }

    @Override
    public void show() {

        MusicManager.getInstance().play();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.defaults().align(Align.center).pad(10);
        table.center();

        title.setColor(Color.RED);
        table.add(title).colspan(3).center();
        table.row().pad(50, 0, 10, 0);
        table.add(name).width(500).colspan(3).center();
        table.row().pad(5, 0, 10, 0);
        table.add(pass).width(500).colspan(3).center();

        table.row().pad(60, 0, 10, 0);
        table.add(back).width(350);
        table.add(login).width(350).padRight(20).padLeft(20);
        table.add(forget).width(350);

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backClicked = true;
            }
        });

        login.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loginClicked = true;
            }
        });

        Drawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        table.setBackground(backgroundDrawable);

        stage.addActor(table);
    }

    @Override
    public void render(float v) {

        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        controller.handleLoginMenuButtons();

        if (!controller.getErrorLabel().getText().isEmpty()) {
            controller.getErrorLabel().setColor(Color.RED);
            controller.getErrorLabel().setPosition(200, 100);
            if (!stage.getActors().contains(controller.getErrorLabel(), true))
                stage.addActor(controller.getErrorLabel());
        }

        this.loginClicked = false;
        this.backClicked = false;
    }

    @Override
    public void resize(int width, int height) {

        if (stage != null)
            stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    public TextField getName() {
        return name;
    }

    public TextField getPass() {
        return pass;
    }

    public TextButton getForget() {
        return forget;
    }

    public boolean isBackClicked() {
        return backClicked;
    }

    public boolean isLoginClicked() {
        return loginClicked;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}


