package com.Graphic.lwjgl3;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.Graphic.Main;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowAdapter;
import com.badlogic.gdx.files.FileHandle;

import static com.Graphic.Main.unrealController;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {

    private static final String TITLE = "20 Min Till Dawn";

    /** Size the window is designed around. Scaled down when it does not fit the monitor. */
    private static final int DESIGN_WIDTH = 1920;
    private static final int DESIGN_HEIGHT = 1080;
    private static final int MIN_WIDTH = 960;
    private static final int MIN_HEIGHT = 540;

    /** Floor for the frame limit, used when the monitor reports no usable refresh rate. */
    private static final int MIN_FPS = 60;

    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle(TITLE);
        //// Vsync limits the frames per second to what your hardware can display, and helps eliminate
        //// screen tearing. This setting doesn't always work on Linux, so the line after is a safeguard.
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match fractional
        //// refresh rates. The Vsync setting above should limit the actual FPS to match the monitor.
        //// Virtual displays, VMs and some drivers report a refresh rate of 0; without the floor below
        //// that would cap the game at 1 FPS and make it look frozen.
        Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
        configuration.setForegroundFPS(Math.max(MIN_FPS, displayMode.refreshRate + 1));
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.

        //// Open at the design size when the monitor is big enough, otherwise shrink to fit it.
        //// Without this the window is larger than the screen on anything below 1080p and part of
        //// the menus ends up off-screen.
        int width = Math.min(DESIGN_WIDTH, displayMode.width);
        int height = Math.min(DESIGN_HEIGHT, displayMode.height);
        if (width < DESIGN_WIDTH || height < DESIGN_HEIGHT) {
            float scale = Math.min(width / (float) DESIGN_WIDTH, height / (float) DESIGN_HEIGHT);
            width = Math.max(MIN_WIDTH, Math.round(DESIGN_WIDTH * scale));
            height = Math.max(MIN_HEIGHT, Math.round(DESIGN_HEIGHT * scale));
        }
        configuration.setWindowedMode(width, height);
        configuration.setWindowSizeLimits(MIN_WIDTH, MIN_HEIGHT, -1, -1);

        //// You can change these files; they are in lwjgl3/src/main/resources/ .
        //// They can also be loaded from the root of assets/ .
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        configuration.setWindowListener(new Lwjgl3WindowAdapter() {
            @Override
            public void filesDropped(String[] files) {
                for (String filePath : files) {
                    FileHandle file = new FileHandle(filePath);
                    if (unrealController != null) {
                        unrealController.onFileDropped(file);
                    }
                }
            }
        });

        return configuration;
    }
}
