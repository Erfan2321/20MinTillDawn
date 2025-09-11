package com.Graphic.Model;

import com.Graphic.Model.GameModel.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class TopBar {

    private ProgressBar progressBar;
    private Label infoLabel;
    private com.badlogic.gdx.scenes.scene2d.ui.Table table;
    private int maxValue;
    private Player player;
    private int currentValue;

    public TopBar(Skin skin, int maxValue, int currentValue, int step) {
        player = Game.getGame().pLayer;
        this.maxValue = maxValue;
        this.currentValue = currentValue;

        progressBar = new ProgressBar(0, maxValue, step, false, skin);
        progressBar.setAnimateDuration(0.2f);
        progressBar.setValue(currentValue);
        progressBar.setWidth(1300);
        progressBar.setHeight(20);
        progressBar.setColor(Color.GRAY);

        infoLabel = new Label(player.getXp() + "/" + player.exNeeded(), skin);
        infoLabel.setAlignment(Align.center);
        infoLabel.setColor(Color.LIGHT_GRAY);

        table = new Table(skin);
        table.top().left().pad(10);
        table.setFillParent(true);
        Stack stack = new Stack();
        stack.add(progressBar);
        stack.add(infoLabel);
        table.add(stack).width(1650).height(20).pad(10).center();
    }

    public void addToStage(Stage stage) {
        stage.addActor(table);
    }

    public void increase(int amount) {
        currentValue += amount;

        infoLabel.setText(player.getXp() + "/" + player.exNeeded());

        if (currentValue > maxValue) currentValue = maxValue;
        progressBar.setValue(currentValue);
    }

    public void reset() {
        currentValue = 0;
        progressBar.setValue(currentValue);
    }
    public void setLabelText(String text) {
        infoLabel.setText(text);
    }
    public void getTable () {

    }
}
