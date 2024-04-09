package org.monkeg.rendering.data.buffers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;
import org.monkeg.api.util.logging.Log;

import java.nio.ByteBuffer;

public class MappedVertexBuffer extends VertexBuffer{
    public ByteBuffer buffer;

    public MappedVertexBuffer(int capacity, VertexBufferLayout layout) {
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        vboId = GL45.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);
        GL45.glBufferStorage(GL45.GL_ARRAY_BUFFER, capacity, GL45.GL_DYNAMIC_STORAGE_BIT | GL45.GL_MAP_WRITE_BIT | GL45.GL_MAP_PERSISTENT_BIT | GL45.GL_MAP_COHERENT_BIT);

        buffer = GL45.glMapBufferRange(GL30.GL_ARRAY_BUFFER, 0, capacity, GL45.GL_MAP_WRITE_BIT
                                                                        | GL45.GL_MAP_PERSISTENT_BIT
                                                                        | GL45.GL_MAP_COHERENT_BIT);

        setLayout(layout);
    }

    @Override
    public void delete() {
        Log.trace("Destroying VAO: {}", vaoId);

        GL45.glUnmapNamedBuffer(vboId);

        GL30.glDisableVertexAttribArray(0);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glDeleteBuffers(vboId);

        GL30.glBindVertexArray(0);
        GL30.glDeleteBuffers(vaoId);
    }
}
