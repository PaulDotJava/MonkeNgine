package org.monkeg.window;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.*;
import org.lwjgl.system.*;
import org.monkeg.api.input.InputManager;
import org.monkeg.api.util.logging.Log;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Window {
    private long window;

    private int width = 1600;
    private int height = 900;

    private InputManager inputManager;

    public Window() {
        init();
        inputManager = new InputManager(window);
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

        Log.info("GLFW initialized!");

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);

        PointerBuffer monitors = glfwGetMonitors();
        assert monitors != null : "No monitor found!";

        int monitorCount = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length;
        
        Log.debug("Monitor count: {}", monitorCount);

        long monitor = NULL;
        if(monitorCount == 1) {
            monitor = monitors.get(0); 
        }else {
            monitor = monitors.get(1);
        }


        int[] monitorOffx = new int[1];
        int[] monitorOffy = new int[1];
        glfwGetMonitorPos(monitor, monitorOffx, monitorOffy);

        Log.debug("Monitor pos: {}, {}", monitorOffx[0], monitorOffy[0]);

        window = glfwCreateWindow(width, height, "Hello World!", 0, 0);
		if ( window == 0 )
			throw new RuntimeException("Failed to create the GLFW window");

        // Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(monitor);
        assert vidmode != null;

		// Center the window
        glfwSetWindowPos(
                window,
                monitorOffx[0] + (vidmode.width() - width) / 2,
                monitorOffy[0] + (vidmode.height() - height) / 2
        );

        Log.trace("Window created!");

        //glfwGetKey(window, GLFW_KEY_A);

        glfwMakeContextCurrent(window);
        Log.trace("OpenGL context current!");
		// Enable v-sync
        int vsync = 1;
		glfwSwapInterval(vsync);
        Log.info("V-Sync: {}", vsync);

		// Make the window visible
		glfwShowWindow(window);
    }

    public void terminate() {
        glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

        glfwTerminate();
		glfwSetErrorCallback(null).free();
    }

    public void swapBuffers() {
        glfwSwapBuffers(window); // swap the color buffers
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}
