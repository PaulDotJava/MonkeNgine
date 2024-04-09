package org.monkeg.rendering.debug.circle;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.monkeg.MonkeNgine;
import org.monkeg.api.util.color.Color;
import org.monkeg.rendering.data.buffers.StaticVertexBuffer;
import org.monkeg.rendering.debug.DebugDrawable;
import org.monkeg.rendering.debug.DebugShader;

public class Circle{
    private Vector2f position;
    private float radius;
    private Color color;

    public Circle(Vector2f position, float radius, Color color) {
        this.position = position;
        this.radius = radius;
        this.color = color;

        MonkeNgine.getInstance().getRenderer().getDebugLayer().addCircle(this);
    }

    public Circle(float x, float y, float radius, Color color) {
        this(
                new Vector2f(x, y),
                radius,
                color
        );
    }

    public Vector2f getPosition() {
        return position;
    }

    public float getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }
}
