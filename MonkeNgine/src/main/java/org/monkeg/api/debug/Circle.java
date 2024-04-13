package org.monkeg.api.debug;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.monkeg.MonkeNgine;
import org.monkeg.api.util.color.Color;
import org.monkeg.rendering.data.buffers.StaticVertexBuffer;
import org.monkeg.rendering.debug.DebugDrawable;
import org.monkeg.rendering.debug.DebugShader;

public class Circle{
    public Vector2f position;
    public float radius;
    public Color color;

    public Circle(Vector2f position, float radius, Color color) {
        this.position = position;
        this.radius = radius;
        this.color = color;
    }

    public Circle(float x, float y, float radius, Color color) {
        this(
                new Vector2f(x, y),
                radius,
                color
        );
    }

    public void add() {
        MonkeNgine.getInstance().getRenderer().getDebugLayer().addCircle(this);
    }
}
