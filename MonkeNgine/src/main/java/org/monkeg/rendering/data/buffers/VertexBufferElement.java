package org.monkeg.rendering.data.buffers;

import org.lwjgl.opengl.GL30;

public class VertexBufferElement {
    public int type;
    public int count;
    public boolean normalized;

    static int getSizeOf(int type) {
        switch (type) {
            case GL30.GL_FLOAT: return 4;
            case GL30.GL_UNSIGNED_INT: return 4;
            case GL30.GL_UNSIGNED_BYTE: return 1;
        }

        assert (false): "Unsupported type!";
        return 0;
    }

    VertexBufferElement(int type, int count, boolean normalized) {
        this.type = type;
        this.count = count;
        this.normalized = normalized;
    }
}
