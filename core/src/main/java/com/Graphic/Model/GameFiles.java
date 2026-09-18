package com.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Where the game keeps the files it writes (accounts and saves).
 *
 * <p>These live in a fixed folder under the user's home directory rather than next to the
 * executable, so progress is found again no matter which directory the game was started from --
 * running {@code ./gradlew lwjgl3:run}, double clicking the jar and launching a packaged build
 * all see the same data.
 */
public final class GameFiles {

    private static final String DATA_DIR = ".20mintilldawn";

    private GameFiles() {
    }

    /**
     * @return a handle to {@code ~/.20mintilldawn/<name>}, migrating a file left behind by older
     * versions in the working directory on first use.
     */
    public static FileHandle data(String name) {

        FileHandle handle = Gdx.files.external(DATA_DIR + "/" + name);
        if (handle.exists())
            return handle;

        FileHandle legacy = Gdx.files.local(name);
        if (legacy.exists()) {
            try {
                legacy.copyTo(handle);
                Gdx.app.log("GameFiles", "Migrated " + name + " to " + handle.path());
            } catch (RuntimeException e) {
                Gdx.app.error("GameFiles", "Could not migrate " + name, e);
                return legacy;
            }
        }
        return handle;
    }
}
