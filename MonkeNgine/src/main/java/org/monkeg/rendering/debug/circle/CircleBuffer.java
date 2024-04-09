package org.monkeg.rendering.debug.circle;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.monkeg.api.util.logging.Log;
import org.monkeg.rendering.data.buffers.MappedVertexBuffer;
import org.monkeg.rendering.data.buffers.VertexBufferLayout;
import org.monkeg.rendering.debug.DebugShader;

import java.nio.ByteBuffer;

public class CircleBuffer {
    private final ByteBuffer localBuffer;
    private final MappedVertexBuffer vbo;

    private static final int VERTEX_SIZE = 7 * 4;
    private static final int CAPACITY = 500;
    private static final int BUFFER_CAPACITY = VERTEX_SIZE * CAPACITY;

    private int circleCount;

    public CircleBuffer() {
        VertexBufferLayout layout = new VertexBufferLayout();
        layout.pushFloat(2);
        layout.pushFloat(4);
        layout.pushFloat(1);

        circleCount = 0;

        vbo = new MappedVertexBuffer(BUFFER_CAPACITY, layout);
        localBuffer = vbo.buffer;
    }

    public void pushCircle(Circle circle) {
        if (circleCount >= CAPACITY - 1) {
            Log.warn("Can't draw any more Debug Circles! (limit = {})", CAPACITY);
            return;
        }

        Vector2f pos = circle.getPosition();
        float[] color = circle.getColor().getRGBA();
        float radius = circle.getRadius();

        localBuffer.putFloat(pos.x);
        localBuffer.putFloat(pos.y);

        localBuffer.putFloat(color[0]);
        localBuffer.putFloat(color[1]);
        localBuffer.putFloat(color[2]);
        localBuffer.putFloat(color[3]);

        localBuffer.putFloat(radius);

        circleCount++;
    }

    public void drawPrep(Matrix4f viewMat, Matrix4f projMat) {
        vbo.bind();

        Log.debug("Drawing {} circles", circleCount);

        DebugShader.circleShader.bind();
        DebugShader.circleShader.setUniformMat4f("u_ProjMat", projMat);
        DebugShader.circleShader.setUniformMat4f("u_ViewMat", viewMat);
    }

    public void clear() {
        localBuffer.clear();
        circleCount = 0;
    }

    public int getVertexCount() {
        return circleCount;
    }
}
