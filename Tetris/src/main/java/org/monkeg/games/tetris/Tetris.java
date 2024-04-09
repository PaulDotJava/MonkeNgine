package org.monkeg.games.tetris;

import org.monkeg.*;
import org.monkeg.api.entity.Sprite;
import org.monkeg.api.input.InputManager;
import org.monkeg.api.instance.Application;
import org.monkeg.api.util.logging.Log;
import org.joml.Vector2f;

public class Tetris extends Application {

    private TitleScreen ts;

    private boolean inGame = false;

    private GameScreen gs;

    public static void main(String[] args) {
        MonkeNgine.getInstance().init();

        MonkeNgine.getInstance().run(new Tetris());

        // TODO make entity cleanup automatic with phantom references
    }

    @Override
    protected void setup() {
        ts = new TitleScreen();
        ts.init();
    }

    @Override
    protected void update(double dt) {
        if (inGame) {
            if (gs.update(dt) == 1) {
                // go back to title screen
            }
            return;
        }

        int tsAction = ts.update();

        if (tsAction == 1) {
            if (gs == null) {
                gs = new GameScreen();
            }
            inGame = true;
            ts.delete();
        } else if (tsAction == -1) {
            terminate();
        }
    }

    @Override
    protected void cleanup() {
        ts.delete();
    }
}
