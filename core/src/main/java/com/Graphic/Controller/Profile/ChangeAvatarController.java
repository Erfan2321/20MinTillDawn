package com.Graphic.Controller.Profile;


import com.Graphic.Main;
import com.Graphic.Model.GameAssetManager;
import com.Graphic.Model.User;
import com.Graphic.Model.UserManager;
import com.Graphic.View.Profile.ChangeAvatar;
import com.Graphic.View.Profile.ProfileMenuView;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ChangeAvatarController {

    private ChangeAvatar view;
    private Label errorLabel;
    private String path;

    public void setView(ChangeAvatar view) {
        this.view = view;
        errorLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
    }


    public void handleLogic () {
        if (view != null) {

            if (view.isAvatarChanged()) {

                Texture avatarPath = null;
                switch (view.getSelectedHero()) {
                    case "Your Profile":
                        avatarPath = GameAssetManager.getGameAssetManager().currentUser.getAvatar();
                        break;

                    case "Dasher":
                        avatarPath = new Texture("Images_grouped_1/Sprite/T/DasherAvatar.png");
                        path = "Images_grouped_1/Sprite/T/DasherAvatar.png";
                        break;

                    case "Diamond":
                        avatarPath = new Texture("Images_grouped_1/Sprite/T/DiamondAvatar.png");
                        path = "Images_grouped_1/Sprite/T/DiamondAvatar.png";
                        break;

                    case "Lilith":
                        avatarPath = new Texture("Images_grouped_1/Sprite/T/LilithAvatar.png");
                        path = "Images_grouped_1/Sprite/T/LilithAvatar.png";
                        break;

                    case "Scarlet":
                        avatarPath = new Texture("Images_grouped_1/Sprite/T/ScarletAvatar.png");
                        path = "Images_grouped_1/Sprite/T/ScarletAvatar.png";
                        break;

                    case "Shana":
                        avatarPath = new Texture("Images_grouped_1/Sprite/T/ShanaAvatar.png");
                        path = "Images_grouped_1/Sprite/T/ShanaAvatar.png";
                        break;

                    default:
                }
                view.changeAvatar(avatarPath);
            }
            else if (view.isBackClicked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            } else if (view.isSubClicked()) {
                Main.getMain().getScreen().dispose();
                User currentUser = GameAssetManager.getGameAssetManager().currentUser;
                if (path != null && !path.isEmpty())
                    currentUser.setAvatarPath(path);
                currentUser.setAvatar(view.getTexture());
                // Without this the new avatar is lost as soon as the game restarts.
                UserManager.saveUser(currentUser);
                Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        }
    }

    public Label getErrorLabel() {
        return errorLabel;
    }
    public void onFileDropped(FileHandle fileHandle) {
        if (fileHandle != null && fileHandle.exists()) {
            path = fileHandle.file().getAbsolutePath();
            view.changeAvatar(new Texture(fileHandle));
        }
    }

}
