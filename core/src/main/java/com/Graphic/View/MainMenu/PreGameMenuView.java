package com.Graphic.View.MainMenu;

import com.Graphic.Controller.MainMenu.PreGameController;
import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class PreGameMenuView implements Screen {

    private static PreGameMenuView preGameMenuView = null;

    private final PreGameController controller;
    public static String languages = "English";
    public final Table table;
    private Stage stage;

    private Label weapon;
    private Label hero;
    private Label min;

    private SelectBox<String> heroBox;
    private SelectBox<String> weaponBox;
    private SelectBox<String> minBox;
    private String selectedWeapon;
    private String selectedHero;
    private String selectedMin;

    private final TextButton back;

    private boolean weaponChanged;
    private boolean heroChanged;
    private boolean backClicked;
    private boolean minChanged;

    Texture backgroundTexture = new Texture(Gdx.files.internal("back11.jpg"));

    private PreGameMenuView (PreGameController controller, Skin skin) {

        this.controller = controller;
        controller.setView(this);
        table = new Table(skin);

        back = new TextButton(Back.getMessage(languages), skin);

        hero = new Label("Hero", skin);
        min = new Label("game time", skin);
        weapon = new Label("Weapon", skin);

        this.minChanged = false;
        this.backClicked = false;
        this.heroChanged = false;
        this.weaponChanged = false;

        minBox = new SelectBox<>(skin);
        heroBox = new SelectBox<>(skin);
        weaponBox = new SelectBox<>(skin);

        minBox.setItems("2", "5", "10", "20");
        heroBox.setItems("SHANA", "DASHER", "LILITH", "SCARLET", "DIAMOND");
        weaponBox.setItems("Revolver", "Shotgun", "SMGsDual");

    }

    public static PreGameMenuView getInstance () {
        if (preGameMenuView == null)
            preGameMenuView = new PreGameMenuView(new PreGameController(), GameAssetManager.getGameAssetManager().getSkin());
        return preGameMenuView;
    }
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.clear();
        table.setFillParent(true);
        table.defaults().align(Align.center).pad(10);
        table.center();

        table.add(weapon);
        table.add(min).padRight(200).padLeft(200);
        table.add(hero);
        table.row().pad(10, 0, 10, 0);

        table.add(weaponBox).width(300);
        table.add(minBox).width(200).padLeft(60).padRight(60);
        table.add(heroBox).width(300);
        table.row().pad(150, 0, 10, 0);

        table.add(back).width(250).padRight(30).colspan(3);

        weaponBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedWeapon = weaponBox.getSelected();
                weaponChanged = true;
            }
        });
        heroBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedHero = heroBox.getSelected();
                heroChanged = true;
            }
        });
        minBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedMin = minBox.getSelected();
                minChanged = true;
            }
        });
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backClicked = true;
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

        controller.handlePreGAmeButtons();

        this.minChanged = false;
        this.backClicked = false;
        this.heroChanged = false;
        this.weaponChanged = false;
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

    public String getSelectedWeapon() {
        return selectedWeapon;
    }
    public String getSelectedHero() {
        return selectedHero;
    }
    public String getSelectedMin() {
        return selectedMin;
    }
    public boolean isBackClicked() {
        return backClicked;
    }
}
