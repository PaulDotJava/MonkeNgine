package org.monkeg.rendering.debug;

import org.joml.Matrix4f;
import org.monkeg.api.debug.Line;
import org.monkeg.api.util.logging.Log;
import org.monkeg.rendering.data.buffers.MappedVertexBuffer;
import org.monkeg.rendering.data.buffers.VertexBufferLayout;

import java.nio.ByteBuffer;

public class LineBuffer {
    private final int VERTEX_SIZE = 5 * 4;
    private final int CAPACITY = 500;

    private final MappedVertexBuffer vbo;
    private final ByteBuffer mappedBuffer;

    private int lineCount;

    public LineBuffer() {
        lineCount = 0;

        VertexBufferLayout layout = new VertexBufferLayout();
        layout.pushFloat(2);
        layout.pushFloat(3);

        vbo = new MappedVertexBuffer(CAPACITY * VERTEX_SIZE, layout);
        mappedBuffer = vbo.buffer;
    }

    public void pushLine(Line l) {
        if(lineCount >= CAPACITY) {
            Log.warn("Can't draw any more debug lines! (limit = {})", CAPACITY);
            return;
        }

        float[] color = l.color.getRGBA();

        // First vertex
        mappedBuffer.putFloat(l.x1);
        mappedBuffer.putFloat(l.y1);

        mappedBuffer.putFloat(color[0]);
        mappedBuffer.putFloat(color[1]);
        mappedBuffer.putFloat(color[2]);

        // Second vertex
        mappedBuffer.putFloat(l.x2);
        mappedBuffer.putFloat(l.y2);

        mappedBuffer.putFloat(color[0]);
        mappedBuffer.putFloat(color[1]);
        mappedBuffer.putFloat(color[2]);

        // TODO: Right now the same color, has to be stored twice. Make it not do that

        lineCount++;
    }

    public void drawPrep(Matrix4f viewMat, Matrix4f projMat) {
        vbo.bind();

        DebugShader.lineShader.bind();
        DebugShader.lineShader.setUniformMat4f("u_ProjMat", projMat);
        DebugShader.lineShader.setUniformMat4f("u_ViewMat", viewMat);
    }

    public int getLineCount() {
        return lineCount;
    }

    public void clear() {
        mappedBuffer.clear();
        lineCount = 0;
    }
}
