package com.Graphic.View.Profile;

import com.Graphic.Controller.Profile.ProfileMenuController;
import com.Graphic.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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


public class ProfileMenuView implements Screen {

    private final ProfileMenuController controller;
    private Stage stage;
    private Table table;

    private TextButton back;
    private TextButton changeUsername;
    private TextButton changePassword;
    private TextButton changeAvatar;
    private TextButton deleteAccountButton;

    private boolean backClicked;
    private boolean changeAvatarClicked;
    private boolean changeUsernameClicked;
    private boolean changePasswordClicked;
    private boolean changeDeleteClicked;

    Texture backgroundTexture = new Texture(Gdx.files.internal("back23.jpg"));

    public ProfileMenuView(ProfileMenuController controller, Skin skin) {

        this.controller = controller;
        controller.setView(this);

        table = new Table(skin);

        back = new TextButton(Back.getMessage(languages), skin);
        changeAvatar = new TextButton(ChangeAvatar.getMessage(languages), skin);
        changePassword = new TextButton(ChangePassword.getMessage(languages), skin);
        changeUsername = new TextButton(ChangeUsername.getMessage(languages), skin);
        deleteAccountButton = new TextButton(DeleteAccount.getMessage(languages), skin);

        backClicked = false;
        changeAvatarClicked = false;
        changeDeleteClicked = false;
        changePasswordClicked = false;
        changeUsernameClicked = false;
    }

    @Override public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.defaults().align(Align.center).pad(10);
        table.center();

        table.add(changeUsername).width(400);
        table.add(changePassword).width(400);
        table.row().pad(50, 0, 10, 0);
        table.add(changeAvatar).width(400);
        table.add(deleteAccountButton).width(400);
        table.row().pad(50, 0, 10, 0);
        table.add(back).colspan(2);

        changeUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeUsernameClicked = true;
            }
        });
        changePassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changePasswordClicked = true;
            }
        });
        changeAvatar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeAvatarClicked = true;
            }
        });
        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeDeleteClicked = true;
            }
        });

        Drawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        table.setBackground(backgroundDrawable);

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backClicked = true;
            }
        });
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        controller.handleMainMenuButtons();

        backClicked = false;
        changeAvatarClicked = false;
        changeDeleteClicked = false;
        changePasswordClicked = false;
        changeUsernameClicked = false;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {

        if (stage != null)
            stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    public boolean isBackClicked() {

        return backClicked;
    }
    public boolean isChangeAvatarClicked() {

        return changeAvatarClicked;
    }
    public boolean isChangeUsernameClicked() {

        return changeUsernameClicked;
    }
    public boolean isChangePasswordClicked() {

        return changePasswordClicked;
    }
    public boolean isChangeDeleteClicked() {

        return changeDeleteClicked;
    }
}
