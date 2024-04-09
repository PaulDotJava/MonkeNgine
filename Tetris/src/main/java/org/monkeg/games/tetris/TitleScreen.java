package org.monkeg.games.tetris;

import org.joml.Vector2f;
import org.monkeg.MonkeNgine;
import org.monkeg.api.entity.Sprite;
import org.monkeg.api.input.InputManager;
import org.monkeg.api.input.Key;
import org.monkeg.api.util.logging.Log;
import org.monkeg.window.Window;

public class TitleScreen {

    public Sprite startButton;
    public Sprite endButton;

    private Hitbox startHB;
    private Hitbox endHB;

    public void init() {
        startButton = new Sprite("src/main/resources/textures/start_button.png");
        int wWidth = MonkeNgine.getInstance().getWindowWidth();
        int wHeight = MonkeNgine.getInstance().getWindowHeight();

        int xPos = wWidth / 2 - startButton.getWidth() / 2;
        int yPos = wHeight / 2 - startButton.getHeight() / 2;

        startButton.setPosition(new Vector2f(xPos, yPos));

        startHB = new Hitbox(xPos, yPos, startButton.getWidth(), startButton.getHeight());

        endButton = new Sprite("src/main/resources/textures/end_button.png");
        yPos = wHeight / 4 - startButton.getHeight() / 2;

        endHB = new Hitbox(xPos, yPos, endButton.getWidth(), endButton.getHeight());

        endButton.setPosition(new Vector2f(xPos, yPos));
    }

    public int update() {

        if(MonkeNgine.getInstance().getInputManager().isMouseButtonPressed(Key.MONKE_MOUSE_BUTTON_LEFT)) {
            Vector2f mousePos = MonkeNgine.getInstance().getInputManager().getMousePosition();
            if (startHB.isInBox(mousePos)) {
                Log.info("starting game");
                return 1;
            } else if (endHB.isInBox(mousePos)) {
                Log.info("Closing application!");
                return -1;
            }
        }

        return 0;

    }

    public void delete() {
        startButton.delete();
        endButton.delete();
    }
}
