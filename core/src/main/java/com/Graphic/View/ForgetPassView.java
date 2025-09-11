package com.Graphic.View;

import com.Graphic.Controller.LoginMenuController;
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

public class ForgetPassView implements Screen {

    private final LoginMenuController controller;
    private Stage stage;
    private Table table;

    private final Label title;

    private final TextField name;
    private final TextField answerSecQ;
    private final TextField newPassword;

    private final TextButton submit;
    private final TextButton back;

    private boolean backClicked;
    private boolean subClicked;

    Texture backgroundTexture = new Texture(Gdx.files.internal("back1.png"));


    public ForgetPassView(LoginMenuController loginMenuController, Skin skin) {

        this.controller = loginMenuController;
        this.table = new Table(skin);

        this.title = new Label(AnswerSecQ.getMessage(languages), skin);

        this.answerSecQ = new TextField("", skin);
        answerSecQ.setMessageText(SecurityQ.getMessage(languages));
        name = new TextField("", skin);
        name.setMessageText(enterName.getMessage(languages));

        this.newPassword = new TextField("", skin);
        newPassword.setMessageText("new password");

        this.submit = new TextButton(Submit.getMessage(languages), skin);
        this.back = new TextButton(Back.getMessage(languages), skin);

        this.backClicked = false;
        this.subClicked = false;

        controller.setView(this);
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.defaults().align(Align.center).pad(10);
        table.center();

        title.setColor(Color.BLUE);
        table.add(title).colspan(2).center();
        table.row().pad(50, 0, 10, 0);
        table.add(name).width(500).colspan(2).center();
        table.row().pad(30, 0, 10, 0);
        table.add(answerSecQ).width(500).colspan(2).center();
        table.row().pad(50, 0, 10, 0);

        table.add(newPassword).width(500).colspan(2).center();
        newPassword.setVisible(false);
        table.row().pad(200, 0, 10, 0);
        table.add(back).width(350);
        table.add(submit).width(350).padLeft(30);


        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backClicked = true;
            }
        });

        submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                subClicked = true;
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

        controller.handleForgetMenuButtons();

        if (!controller.getErrorLabel().getText().isEmpty()) {
            controller.getErrorLabel().setColor(Color.RED);
            controller.getErrorLabel().setPosition(200, 100);
            if (!stage.getActors().contains(controller.getErrorLabel(), true))
                stage.addActor(controller.getErrorLabel());
        }
        else if (controller.getPass() != null) {
            controller.getPass().setPosition(650, 400);
            stage.addActor(controller.getPass());
        }
        this.subClicked = false;
        this.backClicked = false;
    }

    public void resize(int i, int i1) {

    }
    public void pause() {

    }
    public void resume() {

    }
    public void hide() {

    }
    public void dispose() {

    }

    public TextField getName() {
        return name;
    }
    public TextField getAnswerSecQ() {
        return answerSecQ;
    }
    public boolean isSubClicked() {
        return subClicked;
    }
    public boolean isBackClicked() {
        return backClicked;
    }
    public TextField getNewPassword() {
        return newPassword;
    }
}
