package com.Graphic.View.MainMenu;

import com.Graphic.Controller.MainMenu.ScoreBoardController;
import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.User;
import com.Graphic.Model.UserManager;
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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Comparator;

import static com.Graphic.Model.Enum.Message.Back;
import static com.Graphic.View.MainMenu.PreGameMenuView.languages;

public class ScoreboardView implements Screen {

    private final ScoreBoardController controller;
    private Stage stage;
    private Table table;
    private final Skin skin;

    private Label sortLabel;
    private SelectBox<String> sortBy;

    private boolean sortChanged;
    private String selectedSort;

    private final TextButton back;
    private boolean backClicked;

    private ArrayList<User> sortedUsers;

    Texture backgroundTexture = new Texture(Gdx.files.internal("back11.jpg"));

    public ScoreboardView(ScoreBoardController controller,Skin skin) {

        this.controller = controller;
        controller.setView(this);
        table = new Table(skin);
        this.skin = skin;

        back = new TextButton(Back.getMessage(languages), skin);
        this.backClicked = false;

        sortLabel = new Label(" Sorted By ", skin);
        sortBy = new SelectBox<>(skin);
        sortBy.setItems("point", "username", "kiⅼⅼ", "survival time");

        sortedUsers = new ArrayList<>(UserManager.getAllUsers());
        sortedUsers.sort(Comparator.comparingInt(User::getPoint).reversed());
    }

    @Override
    public void show() {

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.clear();
        table.setFillParent(true);
        table.defaults().pad(10).fillX().uniformX();
        table.left();

        table.add(sortLabel).width(200).padLeft(150);
        table.row().pad(30, 0, 10, 0);
        table.add(sortBy).width(300).padLeft(100);
        table.row().pad(150, 0, 10, 0);
        table.add(back).width(250).padLeft(100);

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backClicked = true;
            }
        });
        sortBy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedSort = sortBy.getSelected();
                sortChanged = true;
            }
        });
        Drawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        table.setBackground(backgroundDrawable);
        printBoard();
        stage.addActor(table);
    }


    private void printBoard () {

        User currentUser = GameAssetManager.getGameAssetManager().currentUser;

        int count = 0;
        for (User user : sortedUsers) {
            if (count >= 10) break;

            com.badlogic.gdx.scenes.scene2d.ui.Label row = new Label(
                (count + 1) + ". " + user.getName() +
                    " | Score: " + user.getPoint() +
                    " | Kill: " + user.getMustKill() +
                    " | Time: " + user.getMustTime(),
                skin
            );

            if (user.getName().equals(currentUser.getName()))
                row.setColor(Color.CYAN);
            else if (count == 0)
                row.setColor(Color.GOLD);
            else if (count == 1)
                row.setColor(Color.PINK);
            else if (count == 2)
                row.setColor(Color.BROWN);
            else
                row.setColor(Color.WHITE);

            row.setPosition(800, 600 - count*50);
            stage.addActor(row);
            count++;
        }
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        controller.handleHintButtons();

        if (this.sortChanged) {
            stage.clear();
            show();
            printBoard();
        }
        this.sortChanged = false;

    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }

    public boolean isSortChanged() {
        return sortChanged;
    }

    public String getSelectedSort() {
        return selectedSort;
    }

    public ArrayList<User> getSortedUsers() {
        return sortedUsers;
    }

    public void setSortedUsers(ArrayList<User> sortedUsers) {
        this.sortedUsers = sortedUsers;
    }

    public boolean isBackClicked() {
        return backClicked;
    }
}
