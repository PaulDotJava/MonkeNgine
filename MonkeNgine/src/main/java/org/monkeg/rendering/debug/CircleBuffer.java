package org.monkeg.rendering.debug;

import org.joml.Matrix4f;
import org.monkeg.api.debug.Circle;
import org.monkeg.api.util.logging.Log;
import org.monkeg.rendering.data.buffers.MappedVertexBuffer;
import org.monkeg.rendering.data.buffers.VertexBufferLayout;

import java.nio.ByteBuffer;

public class CircleBuffer {
    private final ByteBuffer mappedBuffer;
    private final MappedVertexBuffer vbo;

    private static final int VERTEX_SIZE = 7 * 4;
    private static final int CAPACITY = 500;

    private int circleCount;

    public CircleBuffer() {
        VertexBufferLayout layout = new VertexBufferLayout();
        layout.pushFloat(2);
        layout.pushFloat(4);
        layout.pushFloat(1);

        circleCount = 0;

        vbo = new MappedVertexBuffer(VERTEX_SIZE * CAPACITY, layout);
        mappedBuffer = vbo.buffer;
    }

    public void pushCircle(Circle c) {
        if (circleCount >= CAPACITY - 1) {
            Log.warn("Can't draw any more Debug Circles! (limit = {})", CAPACITY);
            return;
        }

        mappedBuffer.putFloat(c.position.x);
        mappedBuffer.putFloat(c.position.y);

        float[] color = c.color.getRGBA();

        mappedBuffer.putFloat(color[0]);
        mappedBuffer.putFloat(color[1]);
        mappedBuffer.putFloat(color[2]);
        mappedBuffer.putFloat(color[3]);

        mappedBuffer.putFloat(c.radius);

        circleCount++;
    }

    public void drawPrep(Matrix4f viewMat, Matrix4f projMat) {
        vbo.bind();

        DebugShader.circleShader.bind();
        DebugShader.circleShader.setUniformMat4f("u_ProjMat", projMat);
        DebugShader.circleShader.setUniformMat4f("u_ViewMat", viewMat);
    }

    public void clear() {
        mappedBuffer.clear();
        circleCount = 0;
    }

    public int getCircleCount() {
        return circleCount;
    }
}
