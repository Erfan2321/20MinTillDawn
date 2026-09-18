package com.Graphic.View.Profile;

import com.Graphic.Controller.Profile.ChangeUsernameController;
import com.Graphic.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.Graphic.Model.Enum.Message.*;
import static com.Graphic.View.MainMenu.SettingMenuView.languages;

public class ChangeUsername implements Screen {

    private final ChangeUsernameController controller;
    private Stage stage;
    public Table table;

    private final TextField name;
    private final TextButton back;
    private final TextButton sub;

    private boolean backClicked;
    private boolean subClicked;

    Texture backgroundTexture = new Texture(Gdx.files.internal("back23.jpg"));

    public ChangeUsername (ChangeUsernameController controller, Skin skin) {

        this.controller = controller;
        this.controller.setView(this);

        this.table = new Table(skin);

        this.name = new TextField("", skin);
        name.setMessageText(enterName.getMessage(languages));

        this.back = new TextButton(Back.getMessage(languages), skin);
        this.sub  = new TextButton(Submit.getMessage(languages), skin);

        this.backClicked = false;
        this.subClicked = false;
    }

    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.defaults().align(Align.center).pad(10);
        table.center();

        table.add(name).width(350).colspan(2);
        table.row().pad(55, 0, 10, 0);
        table.add(back).width(250).padRight(30);
        table.add(sub).width(250);

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backClicked = true;
            }
        });
        sub.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                subClicked = true;
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

        controller.handleChangeUsernameButtons();

        if (!controller.getErrorLabel().getText().isEmpty()) {
            controller.getErrorLabel().setColor(Color.RED);
            controller.getErrorLabel().setPosition(200, 100);
            if (!stage.getActors().contains(controller.getErrorLabel(), true))
                stage.addActor(controller.getErrorLabel());
        }

        this.backClicked = false;
        this.subClicked = false;
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
    public TextField getName() {

        return name;
    }
    public boolean isBackClicked() {

        return backClicked;
    }
    public boolean isSubClicked() {

        return subClicked;
    }
}
