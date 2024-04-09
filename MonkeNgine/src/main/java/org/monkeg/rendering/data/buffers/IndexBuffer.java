package org.monkeg.rendering.data.buffers;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import org.monkeg.api.util.logging.Log;

import java.nio.IntBuffer;

public class IndexBuffer {
    private final int IBOId;

    public IndexBuffer(int[] bufferData) {
        IBOId = GL30.glGenBuffers();

        IntBuffer buffer = null;
        try {
            buffer = MemoryUtil.memAllocInt(bufferData.length);
            buffer.put(bufferData).flip();

            bind();
            GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);
            unbind();
        } finally {
            if (buffer != null) {
                MemoryUtil.memFree(buffer);
            }
        }
    }

    public void bind() {
        Log.trace("Binding IBO: {}", IBOId);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, IBOId);
    }

    public void unbind() {
        Log.trace("unbinding IBO: {}", IBOId);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void delete() {
        Log.trace("Destroying IBO: {}", IBOId);

        unbind();

        GL30.glDeleteBuffers(IBOId);
    }
}
