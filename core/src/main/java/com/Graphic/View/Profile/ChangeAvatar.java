package com.Graphic.View.Profile;

import com.Graphic.Controller.Profile.ChangeAvatarController;
import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.Graphic.Model.Enum.Message.Back;
import static com.Graphic.Model.Enum.Message.Submit;
import static com.Graphic.View.MainMenu.SettingMenuView.languages;

public class ChangeAvatar implements Screen {

    private final ChangeAvatarController controller;
    private Stage stage;
    public Table table;

    private Image image;
    private Texture avatar;
    private SelectBox<String> avatarBox;
    private String selectedHero;

    private final TextButton back;
    private final TextButton sub;

    private boolean avatarChanged;
    private boolean backClicked;
    private boolean subClicked;

    Texture backgroundTexture = new Texture(Gdx.files.internal("back23.jpg"));

    public ChangeAvatar (ChangeAvatarController controller, Skin skin) {

        this.controller = controller;
        controller.setView(this);
        Main.unrealController = controller;

        back = new TextButton(Back.getMessage(languages), skin);
        sub = new TextButton(Submit.getMessage(languages), skin);

        this.subClicked = false;
        this.backClicked = false;
        this.avatarChanged = false;

        table = new Table(skin);
        avatar = GameAssetManager.getGameAssetManager().currentUser.getAvatar();
        image = new Image(avatar);
        avatarBox = new SelectBox<>(skin);
        avatarBox.setItems("Your Profile","Dasher", "Diamond", "Lilith", "Scarlet", "Shana");

    }


    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.defaults().align(Align.center).pad(10);
        table.center();

        table.add(image).size(300, 300).padTop(100).colspan(2);
        table.row().pad(100, 0, 10, 0);
        table.add(avatarBox).colspan(2);
        table.row().pad(100, 0, 10, 0);
        table.add(back).width(250).padRight(30);
        table.add(sub).width(250);

        avatarBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedHero = avatarBox.getSelected();
                avatarChanged = true;
            }
        });

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

        controller.handleLogic();

        if (!controller.getErrorLabel().getText().isEmpty()) {
            controller.getErrorLabel().setColor(Color.RED);
            controller.getErrorLabel().setPosition(200, 100);
            if (!stage.getActors().contains(controller.getErrorLabel(), true))
                stage.addActor(controller.getErrorLabel());
        }

        this.subClicked = false;
        this.backClicked = false;
        avatarChanged = false;
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

    public SelectBox<String> getAvatarBox() {
        return avatarBox;
    }
    public boolean isBackClicked() {
        return backClicked;
    }
    public boolean isSubClicked() {
        return subClicked;
    }
    public void setAvatar(Texture avatar) {
        this.avatar = avatar;
    }
    public boolean isAvatarChanged() {
        return avatarChanged;
    }
    public String getSelectedHero() {
        return selectedHero;
    }
    public void changeAvatar(Texture texture) {
        image.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public Texture getTexture () {
        Drawable drawable = image.getDrawable();

        if (drawable instanceof TextureRegionDrawable)
            return ((TextureRegionDrawable) drawable).getRegion().getTexture();
        return null;
    }
 }
