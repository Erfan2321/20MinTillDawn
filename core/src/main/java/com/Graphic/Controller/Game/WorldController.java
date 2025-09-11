package com.Graphic.Controller.Game;

import com.Graphic.Main;
import com.badlogic.gdx.graphics.Texture;

public class WorldController {

    private PlayerController playerController;
    private Texture backgroundTexture;

    public WorldController(PlayerController playerController) {
        this.backgroundTexture = new Texture("back20.png");
        this.playerController = playerController;
    }

    public void update() {

        Main.getBatch().draw(backgroundTexture, 0, 0);
    }
}
