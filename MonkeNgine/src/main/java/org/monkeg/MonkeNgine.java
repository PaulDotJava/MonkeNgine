package org.monkeg;

import org.monkeg.api.entity.Entity;
import org.monkeg.api.input.InputManager;
import org.monkeg.api.instance.Application;
import org.monkeg.api.util.logging.Log;
import org.monkeg.rendering.Renderer;
import org.monkeg.window.Window;

public class MonkeNgine {
    private Window window;
    private Application app;
    private Renderer renderer;

    private static MonkeNgine instance;

    public static MonkeNgine getInstance() {
        if (instance == null)
            instance = new MonkeNgine();

        return instance;
    }

    public void init() {
        Log.init();
        Log.info("Logging initialized!");

        window = new Window();
        Log.info("Window initialized!");
        renderer = new Renderer(getWindowWidth(), getWindowHeight());
        renderer.init();
    }

    public void run(Application app) {
        Log.info("MonkeNgine running...");
        app.gameLoop();
    }

    public Window getWindow() {
        return window;
    }

    public void render() {
        renderer.render();
        window.swapBuffers();
    }

    public void update() {
        window.getInputManager().update();
    }

    public void pollEvents() {
        window.pollEvents();
    }

    public void cleanup() {
        Log.info("MonkeNgine terminating...");

        for (Entity entity : Entity.getEntities()) {
            Log.debug("{} entities", Entity.getEntities().size());
            entity.freeResources();
        }

        Entity.getEntities().clear();

        renderer.cleanup();
        window.terminate();
    }

    public Renderer getRenderer() {
        return renderer;
    }

    // TODO: Multi window support
    public InputManager getInputManager() {
        return window.getInputManager();
    }

    public int getWindowWidth() {
        return window.getWidth();
    }

    public int getWindowHeight() {
        return window.getHeight();
    }

}
