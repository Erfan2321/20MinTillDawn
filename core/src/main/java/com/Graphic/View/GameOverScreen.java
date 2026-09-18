package com.Graphic.View;

import com.Graphic.Controller.SignUpMenuController;
import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.GameModel.Player;
import com.Graphic.Model.User;
import com.Graphic.Model.UserManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOverScreen implements Screen {

    private Stage stage;
    private Skin skin;
    private Player player;
    private float surviveTime; // به ثانیه
    private boolean isWinner;

    Texture backgroundTexture = new Texture(Gdx.files.internal("gameOver.jpg"));


    public GameOverScreen(Player player, float surviveTime, boolean isWinner) {
        this.player = player;
        this.surviveTime = surviveTime;
        this.isWinner = isWinner;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = GameAssetManager.getGameAssetManager().getSkin();

        com.badlogic.gdx.scenes.scene2d.ui.Table table = new Table(skin);
        table.setFillParent(true);
        table.center();

        int kills = player.getKill();
        float score = surviveTime * kills;

        recordResult(kills, (int) score, (int) surviveTime);

        table.add(new Label("Username: " + player.getUsername(), skin)).row();
        table.add(new Label("Survived Time: " + (int)surviveTime + " sec", skin)).row();
        table.add(new Label("Kills: " + kills, skin)).row();
        table.add(new Label("Score: " + (int)score, skin)).row();
        table.add(new Label("Result: " + (isWinner ? "WIN" : "DEAD"), skin)).row();

        TextButton restartButton = new TextButton("Restart", skin);
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new SignUpMenuView(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        table.add(restartButton).padTop(20);
        Drawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        table.setBackground(backgroundDrawable);

        stage.addActor(table);
    }

    /**
     * Writes the finished run back to the signed-in account. Without this the scoreboard and
     * profile always showed zero, because nothing ever updated the stored User.
     */
    private void recordResult(int kills, int score, int surviveTime) {

        User user = GameAssetManager.getGameAssetManager().currentUser;
        // Guests are not part of the roster, so there is nothing to record against.
        if (user == null || !UserManager.userExists(user.getName()))
            return;

        user.setPoint(user.getPoint() + score);
        user.setMustKill(user.getMustKill() + kills);
        user.setMustTime(Math.max(user.getMustTime(), surviveTime));
        UserManager.saveUser(user);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        if (stage != null)
            stage.dispose();

        backgroundTexture.dispose();
        // The skin belongs to GameAssetManager and is shared by every screen, so it is only
        // disposed when the game exits. Disposing it here blanked out all later menus.
    }
}
