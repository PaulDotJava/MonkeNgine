package org.monkeg.rendering.data.buffers;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import org.monkeg.api.util.logging.Log;

import java.nio.FloatBuffer;
import java.util.List;

public class StaticVertexBuffer extends VertexBuffer{
    public StaticVertexBuffer(float[] vertices, VertexBufferLayout layout) {
        genBuffer();
        uploadData(vertices);
        setLayout(layout);
    }

    @Override
    public void delete() {
        Log.trace("Destroying VAO: {}", vaoId);

        GL30.glDisableVertexAttribArray(0);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glDeleteBuffers(vboId);

        GL30.glBindVertexArray(0);
        GL30.glDeleteBuffers(vaoId);
    }

    private void uploadData(float[] data) {
        FloatBuffer floatBuffer = null;
        try {
            floatBuffer = MemoryUtil.memAllocFloat(data.length);
            floatBuffer.put(data).flip();

            bind();

            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, floatBuffer, GL30.GL_DYNAMIC_DRAW);
        }finally {
            if(floatBuffer != null) {
                MemoryUtil.memFree(floatBuffer);
            }
        }
    }

    private void genBuffer() {
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        Log.trace("Creating VAO: {}", vaoId);

        vboId = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);
    }
}
