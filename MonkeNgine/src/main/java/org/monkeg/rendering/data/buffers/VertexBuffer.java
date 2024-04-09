package org.monkeg.rendering.data.buffers;

import org.lwjgl.opengl.GL30;
import org.monkeg.api.util.logging.Log;

import java.util.List;

public abstract class VertexBuffer {
    protected int vaoId, vboId;

    public abstract void delete();

    public void bind() {
        Log.trace("Binding VAO: {}", vaoId);
        GL30.glBindVertexArray(vaoId);
    }

    public void unbind() {
        Log.trace("Unbinding VAO: {}", vaoId);
        GL30.glBindVertexArray(0);
    }

    protected void setLayout(VertexBufferLayout layout) {
        List<VertexBufferElement> elements = layout.getElements();
        int offset = 0;

        for(int i = 0; i < elements.size(); i++) {
            VertexBufferElement element = elements.get(i);

            GL30.glVertexAttribPointer(i, element.count, element.type, element.normalized, layout.getStride(), offset);
            GL30.glEnableVertexAttribArray(i);

            offset += element.count * VertexBufferElement.getSizeOf(element.type);
        }
    }
}
