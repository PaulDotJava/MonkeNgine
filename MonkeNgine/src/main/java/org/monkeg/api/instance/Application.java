package org.monkeg.api.instance;

import org.monkeg.MonkeNgine;
import org.monkeg.api.util.logging.Log;

public abstract class Application {
    private boolean terminating = false;
    public void gameLoop() {
        MonkeNgine monke = MonkeNgine.getInstance();

        setup();

        long last_frame = System.currentTimeMillis();
        while(!monke.getWindow().shouldClose() && !terminating) {
            long cur_time = System.currentTimeMillis();
            double dt = cur_time - last_frame;
            last_frame = cur_time;

            monke.getInputManager().update();
            monke.pollEvents();
            update(dt);
            monke.update();

            monke.render();
        }

        cleanup();
        monke.cleanup();
    }

    protected abstract void setup();

    protected abstract void update(double dt);
    protected abstract void cleanup();

    protected void terminate() {
        Log.info("Initiating shutdown!");
        terminating = true;
    }
}
