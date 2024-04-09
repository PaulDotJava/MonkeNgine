package org.monkeg.rendering.data.buffers;

import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class VertexBufferLayout {
    private int stride;
    private final List<VertexBufferElement> vertexBufferElements;

    public VertexBufferLayout() {
        vertexBufferElements = new ArrayList<>();
    }

    public void pushUnsignedInt(int count) {
        push(count, GL30.GL_UNSIGNED_INT, false);
    }

    public void pushFloat(int count) {
        push(count, GL30.GL_FLOAT, false);
    }

    public int getStride() {
        return stride;
    }

    public List<VertexBufferElement> getElements() {
        return vertexBufferElements;
    }

    private void push(int count, int type, boolean normalized) {
        stride += count * VertexBufferElement.getSizeOf(type);
        vertexBufferElements.add(new VertexBufferElement(type, count, normalized));
    }
}
