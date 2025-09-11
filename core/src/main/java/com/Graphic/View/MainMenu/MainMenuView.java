package com.Graphic.View.MainMenu;

import com.Graphic.Controller.MainMenu.MainMenuController;
import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.User;
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

public class MainMenuView implements Screen {

    private final MainMenuController controller;
    private Stage stage;
    public final Table table;

    private final Label usernameLabel;
    private final Label scoreLabel;
    private final Image avatarImage;

    private final TextButton profileButton;
    private final TextButton scoreboardButton;
    private final TextButton talentButton;
    private final TextButton preGameButton;
    private final TextButton continueGameButton;
    private final TextButton settingsButton;
    private final TextButton newGameButton;
    private final TextButton logoutButton;

    private boolean profileClicked;
    private boolean newGameClicked;
    private boolean scoreboardClicked;
    private boolean talentClicked;
    private boolean preGameClicked;
    private boolean continueGameClicked;
    private boolean settingsClicked;
    private boolean logoutClicked;

    Texture backgroundTexture = new Texture(Gdx.files.internal("back14.png"));

    public MainMenuView(MainMenuController controller, Skin skin) { // TODO وقتی پلی از گوست میزنی یه یوزر گوست ساخته بشه

        table = new Table(skin);

        User currentUser = GameAssetManager.getGameAssetManager().currentUser;
        String username = currentUser.getName();
        int score = currentUser.getPoint();
        Texture avatarTexture = currentUser.getAvatar();
        avatarImage = new Image(avatarTexture);


        usernameLabel = new Label("Username: " + username, skin);
        scoreLabel = new Label("Score: " + score, skin);

        profileButton = new TextButton("Profile", skin);
        scoreboardButton = new TextButton("Scoreboard", skin);
        talentButton = new TextButton("Talent", skin);
        preGameButton = new TextButton("Pre-Game", skin);
        continueGameButton = new TextButton("Continue Game", skin);
        settingsButton = new TextButton("Settings", skin);
        logoutButton = new TextButton("Logout", skin);
        newGameButton = new TextButton("New Game", skin);

        newGameClicked = false;
        profileClicked = false;
        scoreboardClicked = false;
        talentClicked = false;
        preGameClicked = false;
        continueGameClicked = false;
        settingsClicked = false;
        logoutClicked = false;

        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show () {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.defaults().align(Align.center).pad(10);
        table.center();

        table.add(avatarImage).size(100).colspan(6).center();
        table.row();
        table.add(usernameLabel).colspan(6).center();
        table.row();
        table.add(scoreLabel).colspan(6).padBottom(30).center();
        table.row();

        table.add(profileButton).width(250).colspan(2);
        table.add(scoreboardButton).width(250).padLeft(30).padRight(30).colspan(2);
        table.add(talentButton).width(250).colspan(2);
        table.row();
        table.add(preGameButton).width(250).colspan(2);
        table.add(logoutButton).width(250).colspan(2);
        table.add(settingsButton).width(250).colspan(2);
        table.row();
        table.add(continueGameButton).width(350).colspan(3);
        table.add(newGameButton).width(350).colspan(3);

        stage.addActor(table);

        Drawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        table.setBackground(backgroundDrawable);

        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                profileClicked = true;
            }
        });
        talentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                talentClicked = true;
            }
        });
        preGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                preGameClicked = true;
            }
        });
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                newGameClicked = true;
            }
        });
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsClicked = true;
            }
        });
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                logoutClicked = true;
            }
        });

        scoreboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                scoreboardClicked = true;
            }
        });
        continueGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                continueGameClicked = true;
            }
        });
    }

    @Override
    public void render(float v) {

        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        controller.handleMainMenuButtons();

        newGameClicked = false;
        profileClicked = false;
        scoreboardClicked = false;
        talentClicked = false;
        preGameClicked = false;
        continueGameClicked = false;
        settingsClicked = false;
        logoutClicked = false;
    }

    @Override
    public void resize(int i, int i1) {
      //  stage.getViewport().update(width, height, true);

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
        stage.dispose();
    }

    public boolean isNewGameClicked() {
        return newGameClicked;
    }
    public boolean isProfileClicked() {
        return profileClicked;
    }
    public boolean isScoreboardClicked() {
        return scoreboardClicked;
    }
    public boolean isTalentClicked() {
        return talentClicked;
    }
    public boolean isPreGameClicked() {
        return preGameClicked;
    }
    public boolean isContinueGameClicked() {
        return continueGameClicked;
    }
    public boolean isSettingsClicked() {
        return settingsClicked;
    }
    public boolean isLogoutClicked() {
        return logoutClicked;
    }
}
