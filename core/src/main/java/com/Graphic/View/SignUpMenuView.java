package com.Graphic.View;

import com.Graphic.Controller.SignUpMenuController;
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

public class SignUpMenuView implements Screen {

    private final SignUpMenuController controller;
    private Stage stage;
    public Table table;

    private final TextButton finish;
    public  boolean finishClicked;

    private final TextButton playAsGuest;
    public boolean guestClicked;

    private final TextButton login;
    public boolean loginClicked;

    private final Label Title;
    private final TextField pass;
    private final TextField name;
    private final TextField securityAnswer;
    private final Label securityQuestion;

    Texture backgroundTexture = new Texture(Gdx.files.internal("back14.png"));



    public SignUpMenuView (SignUpMenuController controller, Skin skin) {

        this.finishClicked = false;
        this.guestClicked = false;
        loginClicked = false;
        this.login = new TextButton(LoginMenu.getMessage(languages), skin);
        this.finish = new TextButton(SignUp.getMessage(languages), skin);
        this.Title = new Label(SignUpTitle.getMessage(languages), skin);
        this.name = new TextField(enterName.getMessage(languages), skin);
        this.pass = new TextField(enterPass.getMessage(languages), skin);
        securityQuestion = new Label(SecurityQ.getMessage(languages), skin);
        securityAnswer = new TextField("", skin);
        this.playAsGuest = new TextButton(playAsGuest1.getMessage(languages), skin);
        this.controller = controller;
        this.table = new Table();

        controller.setView(this);
    }


    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.defaults().align(Align.center).pad(10);
        table.center();

        Title.setColor(Color.RED);
        securityQuestion.setColor(Color.ROYAL);

        table.add(Title).colspan(3).center();
        table.row().pad(50, 0, 10, 0);
        table.add(name).width(500).colspan(3).center();
        table.row().pad(5, 0, 10, 0);
        table.add(pass).width(500).colspan(3).center();
        table.row().pad(30, 0, 10, 0);
        table.add(securityQuestion).width(500).colspan(3).center();
        table.row().pad(5, 0, 10, 0);
        table.add(securityAnswer).width(500).colspan(3).center();
        table.row().pad(60, 0, 10, 0);
        table.add(playAsGuest).width(350);
        table.add(finish).width(350).padRight(20).padLeft(20);
        table.add(login).width(350);

        finish.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                finishClicked = true;
            }
        });
        playAsGuest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                guestClicked = true;
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

        controller.handleSignUpMenuButtons();

        if (!controller.getErorrLabel().getText().isEmpty()) {
            controller.getErorrLabel().setColor(Color.RED);
            controller.getErorrLabel().setPosition(200, 100);
            if (!stage.getActors().contains(controller.getErorrLabel(), true))
                stage.addActor(controller.getErorrLabel());
        }
        // TODO لاگین فالس نشه؟
        guestClicked = false;
        finishClicked = false;
    }

    @Override
    public void resize(int i, int i1) {

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

    public TextField getPass() {
        return pass;
    }

    public TextField getName() {
        return name;
    }

    public TextField getSecurityAnswer() {
        return securityAnswer;
    }
}
