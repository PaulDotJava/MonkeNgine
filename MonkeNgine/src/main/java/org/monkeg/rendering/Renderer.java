package org.monkeg.rendering;

import org.joml.Matrix4f;
import org.lwjgl.opengl.*;
import org.monkeg.api.entity.Drawable;
import org.monkeg.api.entity.Entity;
import org.monkeg.api.util.logging.Log;
import org.monkeg.rendering.debug.DebugLayer;
import org.monkeg.rendering.debug.DebugShader;
import org.monkeg.rendering.shaders.ShaderProgram;

public class Renderer {
    private final int vieWPortWidth;
    private final int vieWPortHeight;

    private DebugLayer debugLayer;

    private ShaderProgram sp;
    private Matrix4f projectionMat;
    private Matrix4f viewMat;

    public Renderer(int vieWPortWidth, int vieWPortHeight) {
        this.vieWPortWidth = vieWPortWidth;
        this.vieWPortHeight = vieWPortHeight;
    }

    public void init() {
        initGL();

        debugLayer = new DebugLayer();

        sp = new ShaderProgram("/shaders/shader.glsl");
        DebugShader.circleShader = new ShaderProgram("/shaders/debug_circle_shader.glsl");
        DebugShader.lineShader = new ShaderProgram("/shaders/debug_line_shader.glsl");
        sp.bind();

        sp.setUniform1i("u_Sampler", 0);

        projectionMat = new Matrix4f();
        projectionMat.ortho2D(0, vieWPortWidth, 0, vieWPortHeight);

        viewMat = new Matrix4f();

        sp.setUniformMat4f("u_ProjMat", projectionMat);
        sp.setUniformMat4f("u_ViewMat", viewMat);
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        sp.bind();

        for(Entity entity : Entity.getEntities()) {
            if (entity instanceof Drawable) {
                ((Drawable) entity).draw();
                GL30.glDrawElements(GL30.GL_TRIANGLES, 6, GL30.GL_UNSIGNED_INT, 0);
            }
        }

        int circleCount = debugLayer.getCircleCount();

        if(circleCount != 0) {
            Log.trace("Rendering {} circles", circleCount);
            debugLayer.prepCircles(viewMat, projectionMat);
            GL30.glDrawArrays(GL30.GL_POINTS, 0, circleCount);
        }

        int lineCount = debugLayer.getLineCount();
        Log.debug("Rendering {} lines", lineCount);
        if(lineCount != 0) {
            debugLayer.prepLines(viewMat, projectionMat);

            GL30.glDrawArrays(GL30.GL_LINES, 0, lineCount * 2);
        }

        debugLayer.clear();
    }

    public void cleanup() {
        sp.unbind();
        sp.delete();
        DebugShader.circleShader.delete();
    }

    public ShaderProgram getActiveShader() {
        return sp;
    }

    public DebugLayer getDebugLayer() {
        return debugLayer;
    }

    private void initGL() {
        GL.createCapabilities();
        Log.info("OpenGL initialized!");

		GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        GL30.glEnable(GL30.GL_VERTEX_PROGRAM_POINT_SIZE);
        GL30.glEnable(GL30.GL_BLEND);
        GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
    }
}
