package org.monkeg.api.input;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.monkeg.MonkeNgine;
import org.monkeg.api.util.logging.Log;

import java.util.HashSet;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {

    private final long windowKey;

    private Vector2f mouse_pos;

    private final int KEYBOARD_SIZE = 512;

    private boolean[] activeKeys;
    private int[] keyStates;

    private final GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            activeKeys[key] = action != KeyState.MONKE_RELEASE;
            keyStates[key] = action;
        }
    };

    private final GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double xpos, double ypos) {
            // GLFW has (0, 0) in the top left corner of the window, while MonkeNgine has (0, 0) in the bottom right
            // corner, so a conversion of the y value is necessary
            mouse_pos.set(xpos, MonkeNgine.getInstance().getWindow().getHeight() - ypos);
        }
    };

    public InputManager(long windowKey) {
        this.windowKey = windowKey;
        mouse_pos = new Vector2f();

        activeKeys = new boolean[KEYBOARD_SIZE];
        keyStates = new int[KEYBOARD_SIZE];

        glfwSetKeyCallback(windowKey, keyCallback);
        glfwSetCursorPosCallback(windowKey, cursorPosCallback);
    }

    public boolean isKeyPressed(int key) {
        return activeKeys[key];
    }

    public boolean isKeyReleased(int key) {
        return glfwGetKey(windowKey, key) == KeyState.MONKE_RELEASE;
    }

    public boolean isKeyRepeated(int key) {
        return glfwGetKey(windowKey, key) == KeyState.MONKE_REPEAT;
    }

    public boolean isKeyTapped(int key) {
        return keyStates[key] == KeyState.MONKE_PRESS;
    }

    public boolean isMouseButtonPressed(int key) {
        return glfwGetMouseButton(windowKey, key) == KeyState.MONKE_PRESS;
    }

    public boolean isMouseButtonReleased(int key) {
        return glfwGetMouseButton(windowKey, key) == KeyState.MONKE_RELEASE;
    }

    public Vector2f getMousePosition() {
        return mouse_pos;
    }

    public void update() {
        resetKeyboard();
    }

    private void resetKeyboard() {
        for(int i = 0; i < KEYBOARD_SIZE; i++) {
            keyStates[i] = KeyState.MONKE_NO_STATE;
        }
    }
}