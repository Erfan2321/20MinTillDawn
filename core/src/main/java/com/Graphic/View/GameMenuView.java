package com.Graphic.View;

import com.Graphic.Controller.Game.GameMenuController;
import com.Graphic.Main;
import com.Graphic.Model.App;
import com.Graphic.Model.Game;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.GameModel.Player;
import com.Graphic.Model.GameModel.SaveManager;
import com.Graphic.Model.TopBar;
import com.Graphic.View.MainMenu.PreGameMenuView;
import com.Graphic.View.MainMenu.SettingMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.*;

public class GameMenuView implements Screen, InputProcessor {


    private GameMenuController controller;
    private OrthographicCamera camera;
    private Stage levelStage;
    private Table levelTable;
    private Player player;
    private Stage uiStage;
    private Stage stage;
    private Table pause;
    private Skin skin;

    private TopBar topBar;
    private Label timerLabel;
    private Label health;
    private Label kills;
    private Label level;
    private Label ammo;

    private float timeLeft;
    private int MaxTime;
    private int currentlevel;
    private boolean showPauseMenu = false;
    private boolean checker = true;
    private boolean timeUp = false;
    private ArrayList<String> ability;
    private ShapeRenderer shapeRenderer;

    private boolean damagerOn;
    private boolean speedyOn;
    private float speedyTime = 0;
    private float damagerTime = 0;

    private Label AmogreaseAmount;
    private Label ProgreaseAmount;
    private Label VitalityAmount;
    private Label DamagerAmount;
    private Label SpeedyAmount;

    public GameMenuView (Player player1) {

        skin = GameAssetManager.getGameAssetManager().getSkin();
        controller = new GameMenuController();
        this.player = player1;
        controller.setView(this, player);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();

        if (Game.getGame().isNew) {
            if (PreGameMenuView.getInstance().getSelectedMin() != null)
                MaxTime = Integer.parseInt(PreGameMenuView.getInstance().getSelectedMin()) * 60;
            else
                MaxTime = 20 * 60;
            timeLeft = MaxTime;
            speedyTime = MaxTime + 10;
            damagerTime = MaxTime + 10;
        } else {
            MaxTime = (int) Game.getGame().MaxTime;
            timeLeft = Game.getGame().timeLeft;
            speedyTime = timeLeft + 10;
            damagerTime = timeLeft + 10;
        }

        AmogreaseAmount = new Label("Amogrease: " + player.getAmogreaseAbility(), skin);
        ProgreaseAmount = new Label("Progrease: " + player.getProgreaseAbility(), skin);
        VitalityAmount = new Label("Vitality: " + player.getVitalityAbility(), skin);
        DamagerAmount = new Label("Damager: " + player.getDamagerAbility(), skin);
        SpeedyAmount = new Label("Speedy: " + player.getSpeedyAbility(), skin);

        pause = createCheatMenu(skin);
        uiStage = new Stage(new ScreenViewport());
        uiStage.addActor(pause);
        ability = new ArrayList<>();

        currentlevel = 1;
        topBar = new TopBar(skin, player.exNeeded(), player.getXp(), 1);
        timerLabel = new Label("Time: 05:00", skin);
        health = new Label("Hp : " + player.getPlayerHealth(), skin);
        ammo   = new Label("Ammo : " + player.getWeapon().getAmmo(), skin);
        kills  = new Label("Kills : " + player.getKill(), skin);
        level  = new Label("Level : " + player.getLevel(), skin);
        showPauseMenu = false;
        shapeRenderer = new ShapeRenderer();

    }
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        setCustomCursor();
        layoutHud(Gdx.graphics.getHeight());

        topBar.addToStage(stage);
        stage.addActor(ammo);
        stage.addActor(health);
        stage.addActor(kills);
        stage.addActor(level);
        stage.addActor(timerLabel);

    }
    public void render(float v) {
        if (!timeUp) {
            inputController();
            if (!showPauseMenu) {
                if (currentlevel != player.getLevel()) {
                    showSelectLevel();
                } else {
                    controllerTime(v);
                    updateLabels();
                    doNormalRenderTask();
                    checker = true;
                }
            } else
                pauseMenu();
        } else
            End();
    }
    private void showSelectLevel () {

        if (checker) {
            ability = new ArrayList<>();
            if (levelTable != null)
                levelTable.clear();
            levelStage = new Stage(new ScreenViewport());
            levelStage.clear();
            levelTable = createLevelTable();
            levelStage.addActor(levelTable);
            checker = false;
        }
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        levelStage.draw();
    }
    private Table createLevelTable () {
        String[] abilityNames = {
            "VITALITY", "DAMAGER", "PROCREASE", "AMOCREASE", "SPEEDY"
        };

        String[] abilityDescriptions = {
            "Increase max HP by 1 unit",
            "Increase weapon damage by 25% for 10 seconds",
            "Increase weapon projectiles by 1",
            "Increase max ammo count by 5 units",
            "Double player movement speed for 10 seconds"
        };

        List<Integer> indices = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        Collections.shuffle(indices);


        Table table = new Table(GameAssetManager.getGameAssetManager().getSkin());
        table.setFillParent(true);

        for (int i = 0; i < 3; i++) {
            Label nameLabel = new Label(abilityNames[indices.get(i)], skin);
            table.add(nameLabel).expandX().pad(10);
            ability.add(abilityNames[indices.get(i)]);
        }
        table.row();

        for (int i = 0; i < 3; i++) {
            Label descLabel = new Label(abilityDescriptions[indices.get(i)], skin);
            descLabel.setWrap(true);
            table.add(descLabel).width(200).pad(10);
        }
        table.row();

        for (int i = 0; i < 3; i++) {
            Label numLabel = new Label("number  " + (i + 1), skin);
            table.add(numLabel).pad(10);
        }
        return table;
    }
    private void pauseMenu () {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        uiStage.draw();
    }
    private void setAbilityTime () {

        speedyOn = speedyTime - timeLeft < 10;
        damagerOn = damagerTime - timeLeft < 10;
    }
    private void doNormalRenderTask () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Main.getBatch().begin();

        setAbilityTime();
        controller.update(camera, MaxTime-timeLeft, MaxTime, topBar, damagerOn, speedyOn);

        drawHighlightCircle(player.getPosX() + 28, player.getPosY() + 24, 200);
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if (player.getPlayerHealth() <= 0)
            End();
    }
    private void controllerTime (float v) {
        timeLeft -= v;
        if (timeLeft <= 0) {
            timeLeft = 0;
            timeUp = true;
        }
    }
    private void End () {
        int maxTime;
        if (PreGameMenuView.getInstance().getSelectedMin() != null)
            maxTime = Integer.parseInt(PreGameMenuView.getInstance().getSelectedMin()) * 60;
        else
            maxTime = 20 * 60;

        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new GameOverScreen(player, maxTime-timeLeft, timeLeft <= 0));
    }
    private void setTopBar () {

        topBar = new TopBar(skin, player.exNeeded(), player.getXp(), 1);
    }
    private void updateLabels () {
        int minutes = (int) (timeLeft / 60);
        int seconds = (int) (timeLeft % 60);

        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
        health.setText("Hp : " + player.getPlayerHealth());
        kills.setText("Kills : " + player.getKill());
        level.setText("Level : " + player.getLevel());
        ammo.setText("Ammo : " + player.getWeapon().getAmmo());

        AmogreaseAmount.setText("Amogrease: " + player.getAmogreaseAbility());
        ProgreaseAmount.setText("Progrease: " + player.getProgreaseAbility());
        VitalityAmount.setText("Vitality: " + player.getVitalityAbility());
        DamagerAmount.setText("Damager: " + player.getDamagerAbility());
        SpeedyAmount.setText("Speedy: " + player.getSpeedyAbility());
    }
    public Table createCheatMenu(Skin skin) {

        Table table = new Table();
        table.setFillParent(true);
        table.top().center().pad(10);

        Label label = new Label("Cheat Codes", skin, "title");
        label.setColor(Color.RED);
        table.add(label).center().row();

        table.add(new Label("9  -->  go to Boss!", skin)).left().row();
        table.add(new Label("8  -->  infinity ammo", skin)).left().row();
        table.add(new Label("7  -->  increase level", skin)).left().row();
        table.add(new Label("6  -->  increase Health", skin)).left().row();
        table.add(new Label("5  -->  Reduce time to one minute", skin)).left().row();

        table.add(new Label("", skin)).row();

        Label label1 = new Label("Abilities", skin, "title");
        label1.setColor(Color.BLUE);
        table.add(label1).left().row();

        table.add(AmogreaseAmount).left().row();
        table.add(ProgreaseAmount).left().row();
        table.add(VitalityAmount).left().row();
        table.add(DamagerAmount).left().row();
        table.add(SpeedyAmount).left().row();

        table.add(new Label("", skin)).row();

        table.add(new Label("Press 'E' to exit", skin)).left().row();
        table.add(new Label("Press 'S' to save and quit", skin)).left().row();

        return table;
    }
    private void selectAbility (int i) {

        String abilitySelected = ability.get(i-1);

        switch (abilitySelected) {
            case "VITALITY":
                player.increaseHealth(1);
                player.increaseVitalityAbility(1);
                break;
            case "DAMAGER":
                player.increaseDamagerAbility(1);
                damagerOn = true;
                damagerTime = timeLeft;
                break;
            case "PROCREASE":
                player.increaseProgreaseAbility(1);
                player.getWeapon().increaseProjectile(1);
                break;
            case "AMOCREASE":
                player.increaseAmogreaseAbility(1);
                player.getWeapon().increaseMaxAmmo(5);
                break;
            case "SPEEDY":
                player.increaseSpeedyAbility(1);
                speedyOn = true;
                speedyTime = timeLeft;
                break;
            default:
                break;
        }
        currentlevel = player.getLevel();
        setTopBar();
        topBar.addToStage(stage);
    }

    private void drawHighlightCircle(float centerX, float centerY, float radius) {
        Main.getBatch().end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE); // روشن‌تر کردن
        shapeRenderer.setProjectionMatrix(Main.getBatch().getProjectionMatrix());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.2f); // سفید با شفافیت
        shapeRenderer.circle(centerX, centerY, radius);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
        Main.getBatch().begin();
    }
    private void inputController () {

        if (Gdx.input.isKeyJustPressed(App.encape))
            showPauseMenu = !showPauseMenu;

        cheatCodeInput();

        if (currentlevel != player.getLevel()) {

            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1))
                selectAbility(1);
            else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2))
                selectAbility(2);
            else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3))
                selectAbility(3);
        }

        if (showPauseMenu) {
            if (Gdx.input.isKeyPressed(App.Exit))
                End();
            else if (Gdx.input.isKeyPressed(App.moveDownKey)) {
                Game.getGame().MaxTime = MaxTime;
                Game.getGame().timeLeft = timeLeft;
                new SaveManager().saveGame();
                End();
            }
        }
    }
    private void cheatCodeInput () {

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5))
            timeLeft -= 20;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6))
            player.cheatIncreaseHealth();
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7))
            player.cheatLevel();
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            player.getWeapon().setAmmo(Integer.MAX_VALUE - 40);
            player.getWeapon().setMaxAmmo(Integer.MAX_VALUE - 40);
        }
    }
    private void layoutHud (int height) {

        health.setPosition(20, height - 90);
        kills.setPosition(20, height - 120);
        level.setPosition(20, height - 150);
        ammo.setPosition(20, height - 180);
        timerLabel.setPosition(20, height - 210);
    }
    @Override
    public void resize(int width, int height) {

        if (stage != null) stage.getViewport().update(width, height, true);
        if (uiStage != null) uiStage.getViewport().update(width, height, true);
        if (levelStage != null) levelStage.getViewport().update(width, height, true);

        // The world camera is sized in pixels, so it has to follow the window too or the
        // visible play area stops matching where the mouse actually points.
        camera.setToOrtho(false, width, height);
        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();

        layoutHud(height);
    }
    public boolean keyUp(int i) {

        return false;
    }
    public boolean keyDown(int i) {

        return false;
    }
    public boolean keyTyped(char c) {

        return false;
    }
    public boolean scrolled(float v, float v1) {

        return false;
    }
    public boolean touchDragged(int i, int i1, int i2) {

        return false;
    }
    public boolean mouseMoved(int screenX, int screenY) {
        if (!showPauseMenu && SettingMenuView.getInstance().getAimCheckBox().isChecked())
            controller.getWeaponController().setCurser(camera);
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);

        return false;
    }
    public boolean touchUp(int i, int i1, int i2, int i3) {

        return false;
    }
    public boolean touchCancelled(int i, int i1, int i2, int i3) {

        return false;
    }
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!showPauseMenu)
            controller.getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
    }

    public void hide() {

    }
    public void pause() {

    }
    public void resume() {

    }
    public void dispose() {

    }
    private void setCustomCursor() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("Images_grouped_2/Sprite/T/T_CursorSprite.png"));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 16, 16);
        Gdx.graphics.setCursor(customCursor);
        pixmap.dispose();
    }
}
