package org.monkeg.rendering.data;

import org.monkeg.api.util.logging.Log;
import org.monkeg.rendering.data.buffers.IndexBuffer;
import org.monkeg.rendering.data.buffers.StaticVertexBuffer;
import org.monkeg.rendering.data.buffers.VertexBufferLayout;

public class Quad {
    private final StaticVertexBuffer vertexBuffer;
    private final IndexBuffer indexBuffer;

    public Quad(){
        VertexBufferLayout layout = new VertexBufferLayout();
        layout.pushFloat(3);
        layout.pushFloat(2);

        vertexBuffer = new StaticVertexBuffer(new float[] {
            1, 1, 0.0f, 1.0f, 1.0f,
            0, 0, 0.0f, .0f, .0f,
            1, 0, 0.0f, 1.0f, .0f,
            0, 1, 0.0f, .0f, 1.0f
        }, layout);

        indexBuffer = new IndexBuffer(new int[]{
            0, 1, 3,
            0, 1, 2
        });
    }

    public void bind() {
        Log.trace("Binding Quad!");
        vertexBuffer.bind();
        indexBuffer.bind();
    }

    public void unbind() {
        Log.trace("Unbinding Quad!");
        vertexBuffer.unbind();
        indexBuffer.unbind();
    }

    public void delete() {
        Log.trace("Deleting quad!");
        vertexBuffer.delete();
        indexBuffer.delete();
    }
}
