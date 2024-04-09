package org.monkeg;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.monkeg.window.Window;

import static org.lwjgl.opengl.GL11.*;

public class Main {

	// The window handle
	//private long window;
	Window window;

	public void run() {
		window = new Window();

		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		window.terminate();
	}

	private void init() {
		//window.init();
	}

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while(!window.shouldClose()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			window.swapBuffers();

			window.pollEvents();
		}
	}

	public static void main(String[] args) {
		new Main().run();
	}

}