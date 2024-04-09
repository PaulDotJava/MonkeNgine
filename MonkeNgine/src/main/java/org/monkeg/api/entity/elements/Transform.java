package org.monkeg.api.entity.elements;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.monkeg.MonkeNgine;


public class Transform {
    private Vector2f position;
    private Vector2f scale;

    public Transform() {
        scale = new Vector2f();
        position = new Vector2f();
    }

    public void scaleAdditive(float x, float y) {
        scale.x += x;
        scale.y += y;
    }

    public void scale(float x, float y) {
        scale.x = x;
        scale.y = y;
    }

    public void translate(float x , float y) {
        //transform.translate(x, y, 0);
        position.x += x;
        position.y += y;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void bind() {
        Matrix4f transform = new Matrix4f();
        transform.translate(position.x, position.y, 0);
        transform.scale(scale.x, scale.y, 1);
        //transform.scale(100f, 100f, 1);

        MonkeNgine.getInstance().getRenderer().getActiveShader().setUniformMat4f("u_ModelMat", transform);
    }

    public Vector2f getPosition() {
        return position;
    }
}
